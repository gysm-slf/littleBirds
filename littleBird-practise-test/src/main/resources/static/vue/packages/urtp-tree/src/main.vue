<template>
  <div class="cls-tree">
    <slot name="title_bar">
<!--      <h6 class="default_title">
        urtp-tree
      </h6>-->
    </slot>
    <div>
      <el-input v-if="withSearch" placeholder="输入关键字进行过滤" v-model="matchWords"></el-input>
    </div>
    <!--  不关联  -->
    <el-tree ref="tree"
             :class="withSearch ? 'filter-tree' : ''"
             :data="data"
             :node-key="dataModel.id_key"
             :with-search="withSearch"
             :show-checkbox="showCheckbox"
             :default-checked-keys="defaultCheckedKeys"
             :lazy="lazy"
             :default-expanded-keys="defaultExpandedKeys"
             :highlight-current="highlightCurrent"
             :default-expand-all="defaultExpandAll"
             :expand-on-click-node="expandOnClickNode"
             :check-on-click-node="checkOnClickNode"
             :check-strictly="checkStrictly"
             :props="props"
             :load="loadChildNode"
             :filter-node-method="filterNode"
             @node-click="handleNodeClick"
             @check-change="checkChangeLogicJudgment"
             @check="handleCheck">
    </el-tree>
  </div>
</template>

<script>
export default {
  name: "urtp-tree",
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      }
    },
    nodeKey: {
      type: String,
      default: 'sys_code'
    },
    withSearch: {
      type: Boolean,
      default: false
    },
    showCheckbox: {
      type: Boolean,
      default: true
    },
    defaultCheckedKeys: {
      type: Array,
      default: () => {
        return [];
      }
    },
    lazy: {
      type: Boolean,
      default: false
    },
    load: {
      type: Function
    },
    defaultExpandedKeys: {
      type: Array,
      default: () => {
        return [];
      }
    },
    highlightCurrent: {
      type: Boolean,
      default: false
    },
    defaultExpandAll: {
      type: Boolean,
      default: false
    },
    expandOnClickNode: {
      type: Boolean,
      default: true
    },
    checkOnClickNode: {
      type: Boolean,
      default: false
    },
    checkStrictly: {
      type: Boolean,
      default: false
    },
    props: {
      type: Object,
      default: () => {
        return {
          label: 'label',
          children: 'children',
          isLeaf: function (data, node) {
            // return node.data['child_flag'] === '1' ? false : true;
            return node.data['children'] ? false : true;
          }/*,
          disabled: function (data, node) {
            if (data.class_grade == '1') {
              return true
            } else {
              return false
            }
          }*/
        }
      }
    },
    dataModel: {
      type: Object,
      default: () => {
        return {
          id_key: 'sys_code',
          label_key: 'class_name',
          value_key: 'class_code',
          grade_key: 'class_grade',
          isHaveChildren_key: 'child_flag',
          children_key: 'children'
        }
      }
    }
  },
  watch: {
    matchWords(val) {
      if(this.withSearch)
        this.$refs.tree.filter(val);
    }
  },
  data() {
    return {
      selectItems: [],
      matchWords: '',
      /* 全选标志 某些情况下全选则可以不添加检索条件 以此为依据 */
      isSelectedAll: false
    };
  },
  mounted() {
    if(!this.lazy &&
        this.dataModel.id_key == 'sys_code' &&
        this.dataModel.value_key == 'class_code' &&
        this.dataModel.grade_key == 'class_grade' &&
        this.dataModel.isHaveChildren_key == 'child_flag' &&
        this.dataModel.children_key == 'children')
      this.data = this.toTreeJson(this.data, 1);
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data[this.dataModel.label_key].indexOf(value) !== -1;
    },
    handleNodeClick(arg1, arg2, arg3){
      this.$emit('node-click', arg1, arg2, arg3);
    },
    handleCheck(arg1, arg2){
      this.$emit('check', arg1, arg2);
    },
    commonCheckChange(data, checked, indeterminate){
      this.selectItems = [];
      let res = this.$refs.tree.getCheckedNodes();
      res.forEach((item) => {
        this.selectItems.push({
          'id': item[this.dataModel.id_key],
          'label': item[this.dataModel.label_key],
          'value': item[this.dataModel.value_key]
        })
      });
      let all = '';
      if(this.checkStrictly){
        if (this.data.length == this.selectItems.length) {
          for (let i = 0; i < this.data.length; i++)
            all += this.data[i][this.dataModel.value_key] + this.data[i][this.dataModel.label_key];

          this.isSelectedAll = true;
          for (let i = 0; i < this.selectItems.length; i++) {
            if (all.indexOf(this.selectItems[i]['value'] + this.selectItems[i]['label']) == -1)
              this.isSelectedAll = false;
          }
        }
      }else{
        for (let i = 0; i < this.selectItems.length; i++)
          all += this.selectItems[i]['value'] + this.selectItems[i]['label'];

        this.isSelectedAll = true;
        for (let i = 0; i < this.data.length; i++) {
          if (all.indexOf(this.data[i][this.dataModel.value_key] + this.data[i][this.dataModel.label_key]) == -1)
            this.isSelectedAll = false;
        }
      }
      this.$emit('check-change', data, checked, indeterminate, this.selectItems, this.isSelectedAll);
    },
    checkChangeLogicJudgment(data, checked, indeterminate){
      this.checkStrictly ? this.handleCheckChangeStrictly(data, checked, indeterminate) : this.handleCheckChange(data, checked, indeterminate);
    },
    handleCheckChange(data, checked, indeterminate) {
      this.commonCheckChange(data, checked, indeterminate);
    },
    handleCheckChangeStrictly(data, checked, indeterminate){
      this.resizeParentChecked(this.$refs.tree, data, checked);
      this.resizeChildChecked(this.$refs.tree, data, checked);
      this.commonCheckChange(data, checked, indeterminate);
    },
    resizeChildChecked(tree, data, checked) {
      if (typeof data[this.dataModel.children_key] == 'undefined')
        return;
      if (data[this.dataModel.isHaveChildren_key] == "1" && checked) {
        for (let i = 0; i < data[this.dataModel.children_key].length; i++) {
          tree.setChecked(data[this.dataModel.children_key][i][this.dataModel.id_key], false);
          if (data[this.dataModel.children_key][i][this.dataModel.isHaveChildren_key] == "1")
            this.resizeChildChecked(tree, data[this.dataModel.children_key][i], true);
        }
      }
    },
    resizeParentChecked(tree, data, checked) {
      if (checked) {
        let t = parseInt(data[this.dataModel.grade_key]) - 1;
        for (let i = t; i > 0; i--)
          tree.setChecked(data[this.dataModel.id_key].substring(0, i * 4), false);
      }
    },
    loadChildNode(node, resolve) {
      return this.lazy ? (typeof this.load == 'function' ? this.load(node, resolve) : resolve([])) : resolve[this.data];
    },
    toTreeJson(arr, rootGrade) {
      //result tree data
      let td = [];
      for (let i = 0; i < arr.length; i++) {
        let grade = arr[i][this.dataModel.grade_key];
        while (td.length < grade) {
          td.push([]);
        }
        td[grade - 1].push(arr[i]);
      }

      td.filter(e => {
        return e.length > 0;
      })

      for (let i = td.length - 2; i > -1; i--) {
        for (let j = 0; j < td[i].length; j++) {
          if (td[i][j][this.dataModel.isHaveChildren_key] == '1') {
            td[i][j][this.dataModel.children_key] = this.getChild(td[td[i][j][this.dataModel.grade_key]], td[i][j][this.dataModel.id_key], 4);
          }
        }
      }
      return td[rootGrade - 1];
    },
    getChild(arr, code, cLen) {
      let tmp = [];
      for (let i = 0; i < arr.length; i++) {
        let c = arr[i][this.dataModel.id_key];
        if (c.startsWith(code) && c.length == cLen * arr[i][this.dataModel.grade_key]) {
          tmp.push(arr[i]);
        }
      }
      return tmp;
    },
    getTreeRef(){
      return this.$refs.tree;
    }
  }
}

</script>

<style lang="scss">
.cls-tree {
  width: 320px;
}

.default_title {
  line-height: 50px;
  background-color: rgb(20, 150, 233);
  color: rgb(255, 255, 255);
  text-align: left;
  padding-left: 20px;
  margin-bottom: 0px;
  border: 1px solid #fff;
}

.c_tree {
  .el-tree-node {
    white-space: normal;
    .el-tree-node__content {
      height: 100%;
      align-items: start;
    }
  }
}

</style>
