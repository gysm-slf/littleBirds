<template>
  <div class="dialog2" v-show="showSource">
    <div class="dialog-container2">
      <div class="cj_center">
        <div class="dialog-title2" style="text-align: left;">{{title}}</div>
        <div class="close-btn" @click="closeSource"><i class="close_font">关闭</i></div>
      </div>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="基金选择" name="fund-popup">
          <div class="search_row adv_flex cj_center">
            <span class="search_font">检索项</span>
            <span class="search_l_10">
              <el-select v-model="selectValue" placeholder="请选择">
                <el-option v-for="item in selectOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </span>
            <span class="search_font search_l_20">检索词</span>
            <span class="search_l_10">
              <el-input placeholder="请输入内容" v-model="searchWord" @keyup.enter.native="fundinfo('search')" clearable>
              </el-input>
            </span>
            <span class="search_l_20">
              <el-button type="primary" icon="el-icon-search" size="small" @click="fundinfo('search')">搜索</el-button>
            </span>
          </div>
          <el-divider></el-divider>
          <div style="display: none;" class="senior-cond-item" type="items" bind_name="fund_items"></div>
          <div class="tree_content">
            <urtp-tree ref="tree"
                       :data="results.dataList"
                       :data-model="dataModel"
                       :props="defaultProps"
                       :highlight-current="true"
                       @check-change="handleNodeChange">
            </urtp-tree>
            <div class="jou_page">
              <uui-pagination :total='results.total' :show-total="true" :page-sizes="[pageSize]" :index='curPage'
                              small @current-change="currentChange" @size-change="sizeChange">
              </uui-pagination>
            </div>
          </div>
          <el-divider></el-divider>
        </el-tab-pane>
        <div class="jou_clear">
          <a class="font_clear" @click="clearSelectData">清除全部选择</a>
        </div>
        <div class="select_tree">
          <ul>
            <li v-for="(item,index) in selectData" :key="index">
              <el-checkbox v-model="item.checked" @change="changeSelect(item)">{{item.label}}</el-checkbox>
            </li>
          </ul>
        </div>
        <div class="btns">
          <div class="confirm-btn" @click="clickConfirm">确定</div>
        </div>
      </el-tabs>
    </div>
  </div>

</template>
<script>
import {encrypt} from "urtp-aes-encrypt";

  export default {
    name: 'fund-popup',
    props: {
      _results: {
        type: Object,
        default: () => ({
          total: 0,
          dataList: []
        })
      },
      value: {
        type: Boolean,
        default: false
      },
      title: {
        type: String,
        default: "基金选择"
      },
      selectOptions: {
        type: Array,
        default: () => [{
          value: '基金通用名称',
          label: '基金名称'
        }, {
          value: '管理机构通用名称',
          label: '基金管理单位'
        },]
      },
      configs: {
        type: Object,
        default: () => ({
          sysId: '113'
        })
      },
      cust_key: {
        type: String,
        default: "law8_fund_info"
      },
    },
    watch: {
      _results: {
        handler(results) {
          this.results = results;
        },
        deep: true,
        immediate: true
      },
      value: {
        handler(newVal, oldVal) {
          this.showSource = newVal;
          if(newVal)
            this.selectTemp = this.selectData;
        },
        deep: true,
        immediate: true
      },
      showSource: {
        handler(val) {
          this.$emit("input", val);
        },
        deep: true,
        immediate: true
      },
      selectData: {
        handler(newValue, oldValue) {},
        deep: true,
        immediate: true
      }
    },
    data() {
      return {
        conf: {
          url: 'http://localhost:8888'
        },
        results: {
          total: 0,
          dataList: []
        },
        curPage: 1,
        pageSize: 10,
        total: 0,
        showSource: false,
        activeName: 'fund-popup',
        selectValue: '基金通用名称',
        fundOrg: '',
        searchWord: '',
        selectTemp: [],
        data: [],
        defaultProps: {
          id: 'fund_code',
          label: 'fund_name'
        },
        selectData: [],
        selectSetData: new Set(),
        selectDataTemp: [],
        fund_items: [],
        where_format: '',
        limit: '',
        dataModel: {
          id_key: 'fund_code',
          label_key: 'fund_name',
          value_key: 'fund_code'
        }
      };
    },
    created() {
      this.limit = ' ' + ((this.curPage - 1) * this.pageSize) + ',' + this.pageSize;
      this.fundinfo();
    },
    mounted() {
      this.showSource = this.value;
    },
    methods: {
      triggerSearch(event){
        let code = event.keyCode || event.which;
        if (code == 13)
          this.fundinfo('search');
      },
      //基金基本信息查询
      fundinfo(trigger) {
        if(!!trigger && trigger == 'search'){
          this.curPage = 1;
          this.limit = ' ' + ((this.curPage - 1) * this.pageSize) + ',' + this.pageSize;
        }
        if(!!this.searchWord)
          this.where_format = ' ' + this.selectValue + ' %= "' + this.searchWord + '"';
        else
          this.where_format = '';

        this.$emit('searchExecute', this.configs.sysId, this.cust_key, this.where_format, this.limit);
      },
      searchExecute(key) {
        let _this = this;

        this.getCustomDataTable(this.configs.sysId, this.cust_key, this.where_format, this.limit).then(function (response){
          if(response.data.code == '200'){
            _this['data'] = response.data.dataList;
            _this.total = response.data.total;
          }
        });
      },
      getCustomDataTable (sysId, custkey, where_format, limit, values) {
        return this.axios.post(this.conf['url'].concat('/api/productdata/get_custom_data/' + sysId + '/' + custkey + '?where_format=' + encrypt(where_format) + '&limit=' + limit +  '&values=' + (!!values ? values : '')));
      },
      Exists() {},
      removeAll() {},
      closeSource() {
        this.showSource = false;
        if(this.selectTemp.length == 0){
          let checked = this.$refs.tree.getTreeRef().getCheckedNodes();
          for (let i = 0; i < checked.length; i++) {
            this.$refs.tree.getTreeRef().setChecked(checked[i][this.defaultProps.id], false);
          }
        }else{
          for (let l = 0; l < this.data.length; l++)
            this.$refs.tree.getTreeRef().setChecked(this.data[l]['name_code'], false);
          for (let i = 0; i < this.selectTemp.length; i++) {
            this.$refs.tree.getTreeRef().setChecked(this.selectTemp[i].id, this.selectTemp[i].checked);
          }
        }
      },
      closeBtn() {
        // this.$emit("cancel");
        this.closeSource();
      },
      clickConfirm() {
        this.$emit("getFundSelectItem", this.selectData);
        this.showSource = false;
      },
      currentChange(val, id) {
        this.selectDataTemp = this.selectData.slice();
        this.curPage = val;
        this.limit = ' ' + ((this.curPage - 1) * this.pageSize) + ',' + this.pageSize;
        this.fundinfo();
      },
      sizeChange(val) {
      },
      handleNodeChange(data, checked) {
        if(checked){
          if(!this.selectSetData.has(data[this.defaultProps.id] + data[this.defaultProps.label])){
            this.selectSetData.add(data[this.defaultProps.id] + data[this.defaultProps.label]);
            this.selectData.push({
              id: data[this.defaultProps.id],
              label: data[this.defaultProps.label],
              checked: true
            });
          }
        }else {
          if(this.selectSetData.has(data[this.defaultProps.id] + data[this.defaultProps.label])){
            this.selectSetData.delete(data[this.defaultProps.id] + data[this.defaultProps.label]);
            for (let i = 0; i < this.selectData.length; i++) {
              if(this.selectData[i].id == data[this.defaultProps.id])
                this.selectData.splice(i, 1);
            }
          }
        }
      },
      changeSelect(item) {
        for (let i = 0; i < this.selectData.length; i++) {
          if (this.selectData[i]['id'] == item['id'] && item.checked == false) {
            this.$refs.tree.getTreeRef().setChecked(item['id'], false, false);
            return;
          };
        }
      },
      clearSelectData(){
        let _this = this;
        let arr_0 = this.$refs.tree.getTreeRef().getCheckedNodes();
        arr_0.forEach(e => {
          _this.$refs.tree.getTreeRef().setChecked(e[_this.defaultProps.id], false);
        });
        this.selectData = [];
      },
    },
  };

</script>
<style lang="scss">
  .dialog2 {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    z-index: 999;

    .dialog-container2 {
      width: 800px;
      background: #ffffff;
      top: 40%;
      left: 50%;
      transform: translate(-50%, -50%);
      border-radius: 8px;
      position: relative;
      overflow-y: auto;

      i {
        font-style: normal;
      }

      .close_font:hover {
        font-weight: bold;
        cursor: pointer;
      }

      .dialog-title2 {
        width: 100%;
        height: 60px;
        font-size: 18px;
        color: #696969;
        font-weight: 600;
        padding: 16px 50px 0 20px;
        box-sizing: border-box;
      }

      .close-btn {
        width: 100px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        font-size: 14px;
        color: #9D9D9D;
      }

      .search_row {
        margin-left: 20px;
        margin-right: 20px;
      }

      .search_font {
        font-size: 15px;
        color: #8E8E8E;
      }

      .search_l_10 {
        margin-left: 10px;
      }

      .search_l_20 {
        margin-left: 20px;
      }

      .el-input__inner {
        border-radius: 2px !important;
      }

      .tree_content {
        height: 280px;
      }

      .select_tree {
        border: solid 1px #c8dcee;
        background-color: #f1f7fb;
        height: 100px;
        min-height: 65px;
        overflow-y: auto;
        margin: 6px;
        padding-left: 10px;
      }

      .el-pagination__sizes {
        display: none;
      }

      .jou_page {
        margin-left: 20px;
        margin-top: 10px;
        margin-bottom: 10px;
      }

      .jou_clear {
        margin: 0px 0px 5px 10px;
      }

      .font_clear {
        font-size: 13px;
      }

      .el-divider--horizontal {
        margin: 20px 0 !important;
      }

      a:hover,
      a:active {
        color: #BEBEBE;
        text-decoration: none;
        cursor: pointer;
      }

      .btns {
        width: 100%;
        height: 48px;
        position: absolute;
        bottom: 0;
        left: 0;
        text-align: right;
        padding: 0 16px;
        box-sizing: border-box;

        &>div {
          display: inline-block;
          height: 30px;
          line-height: 30px;
          padding: 0 14px;
          color: #ffffff;
          background: #f1f1f1;
          border-radius: 8px;
          margin-right: 12px;
          cursor: pointer;
        }

        .confirm-btn {

          background: #ef8c8c;

          &:hover {
            background: rgb(224, 135, 135);
          }

          &:active {
            background: #ef8c8c;
          }
        }
      }
    }
  }

</style>

<style>
.cj_center {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
}
.adv_flex {
  -webkit-box-pack: start;
  justify-content: flex-start;
}
.dialog2 ul {
  list-style: none;
  margin: 0px;
  padding: 0px;
}
.dialog2 ul li {
  text-align: left;
  margin-top: 5px;
}
.jou_clear {
  text-align: left;
}
</style>