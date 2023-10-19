<template>
  <div class="dialog2" v-show="showSource" >
    <div class="dialog-container2">
      <div class="cj_center">
        <div class="dialog-title2">{{title}}</div>
        <div class="close-btn" @click="closeSource"><i class="close_font">关闭</i></div>
      </div>
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane v-if="show_content.toUpperCase() == 'CLKJ' || show_content.toUpperCase() == 'ALL'" label="期刊来源" name="clkj-popup">
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
              <el-input placeholder="请输入内容" v-model="searchWord" @keyup.enter.native="infoinit" clearable>
              </el-input>
            </span>
            <span class="search_l_20">
              <el-button type="primary" icon="el-icon-search" size="small" @click="infoinit">搜索</el-button>
            </span>
          </div>
          <el-divider></el-divider>
          <div class="tree_content">
            <urtp-tree ref="clkj_tree"
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
        <el-tab-pane v-if="show_content.toUpperCase() == 'CLKN' || show_content.toUpperCase() == 'ALL'" label="报纸来源" name="clkn-popup">
          <div class="search_row adv_flex cj_center">
            <span class="search_font">检索项</span>
            <span class="search_l_10">
              <el-select v-model="selectNewsValue" placeholder="请选择">
                <el-option v-for="im in selectNewsOptions" :key="im.value" :label="im.label" :value="im.value">
                </el-option>
              </el-select>
            </span>
            <span class="search_font search_l_20">检索词</span>
            <span class="search_l_10">
              <el-input placeholder="请输入内容" v-model="searchNewsWord" @keyup.enter.native="paperinfo" clearable>
              </el-input>
            </span>
            <span class="search_l_20">
              <el-button type="primary" icon="el-icon-search" size="small" @click="paperinfo">搜索</el-button>
            </span>
          </div>
          <el-divider></el-divider>
          <div class="tree_content">
            <urtp-tree ref="clkn_tree"
                       :data="results.news_dataList"
                       :data-model="dataModel"
                       :props="defaultProps"
                       :highlight-current="true"
                       @check-change="handleNodeChange">
            </urtp-tree>
            <div class="jou_page">
              <uui-pagination  :total='results.total' :show-total="true" :page-sizes="[pageSize]" :index='curPage'
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
  export default {
    name: 'source-popup',
    props: {
      _results: {
        type: Object,
        default: () => ({
          total: 0,
          dataList: [],
          news_dataList: []
        })
      },
      value: {
        type: Boolean,
        default: false
      },
      show_type: {
        type: String,
        default: "CLKJ"
      },
      title: {
        type: String,
        default: "文献来源"
      },
      selectOptions: {
        type: Array,
        default: () => [{
          value: 'source_cykm',
          label: '期刊名称'
        }, {
          value: 'ISSN',
          label: 'ISSN'
        }, {
          value: 'CN',
          label: 'CN'
        }]
      },
      selectNewsOptions: {
        type: Array,
        default: () => [{
          value: 'source_name',
          label: '报纸名称'
        }, {
          value: 'cn',
          label: '国内统一刊号'
        }]
      },
      configs: {
        type: Object,
        default: () => ({
          sysId: '113',
          CLKJ_tableId: '538',
          CLKN_tableId: '545'
        })
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
        handler(newValue, oldValue) {
          this.showSource = newValue;
          if(newValue)
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
      },
      show_type: {
        handler(val) {
          this.show_content = val;

          if(!this.isExecute_CLKJ && this.data.length == 0 && (this.show_content.toUpperCase() == 'CLKJ' || this.show_content.toUpperCase() == 'ALL')){
            this.infoinit();
            this.isExecute_CLKJ = true;
          }
          if(!this.isExecute_CLKN && this.newsData.length == 0 && ((this.show_content.toUpperCase() == 'CLKN' || this.show_content.toUpperCase() == 'ALL'))){
            this.paperinfo();
            this.isExecute_CLKN = true;
          }
        },
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
          dataList: [],
          news_dataList: []
        },
        curPage: 1,
        pageSize: 10,
        total: 0,
        show_content: this.show_type,
        showSource: false,
        activeName: 'clkj-popup',
        selectTemp: [],
        selectValue: '',
        selectNewsValue: '',
        searchWord: '',
        searchNewsWord: '',
        data: [],
        newsData: [],
        defaultProps: {
          id: 'source_code',
          label: 'source_name'
        },
        dataModel: {
          id_key: 'source_code',
          label_key: 'source_name',
          value_key: 'source_code'
        },
        selectData: [],
        selectSetData: new Set(),
        CLKJ_items: [],
        isExecute_CLKJ: false,
        CLKN_items: [],
        isExecute_CLKN: false,
      };
    },
    created() {
      if(!this.isExecute_CLKJ && this.data.length == 0 && (this.show_content.toUpperCase() == 'CLKJ' || this.show_content.toUpperCase() == 'ALL')){
        this.infoinit();
        this.isExecute_CLKJ = true;
      }
      if(!this.isExecute_CLKN && this.newsData.length == 0 && (this.show_content.toUpperCase() == 'CLKN' || this.show_content.toUpperCase() == 'ALL')){
        this.paperinfo();
        this.isExecute_CLKN = true;
      }
    },
    mounted() {
      this.showSource = this.value;
    },
    methods: {
      //切换显示方式
      handleClick(tab, event) {
        this.curPage = 1;

        if(event.target.getAttribute('id') == 'tab-clkj-popup'){
          if(this.data.length == 0)
            this.infoinit();
        }else{
          if(this.newsData.length == 0)
            this.paperinfo();
        }
      },
      //报纸基本信息查询
      paperinfo() {
        let queryModel = new this.$queryModel();
        queryModel.setSysId(this.configs.sysId);
        queryModel.setTableId(this.configs.CLKN_tableId);
        queryModel.setStart((this.curPage - 1) * this.pageSize);
        queryModel.setLength(this.pageSize);
        if(!!this.searchNewsWord)
          queryModel.newSrchItem(this.selectNewsValue, this.searchNewsWord);
        this.$emit('searchExecute', queryModel, 'news_dataList');
      },
      //期刊基本信息查询
      infoinit() {
        let queryModel = new this.$queryModel();
        queryModel.setSysId(this.configs.sysId);
        queryModel.setTableId(this.configs.CLKJ_tableId);
        queryModel.setStart((this.curPage - 1) * this.pageSize);
        queryModel.setLength(this.pageSize);
        if(!!this.searchWord)
          queryModel.newSrchItem(this.selectValue, this.searchWord);
        this.$emit('searchExecute', queryModel, 'dataList');
      },
      searchExecute(queryModel, key) {
        let _this = this;
        this.dataSearchData(queryModel.toJson()).then(function (response){
          if(response.data.code == '200'){
            if(key == 'data'){
              for (let i = 0; i < response.data.dataList.length; i++)
                response.data.dataList[i]['type'] = 'CLKJ';
            }else{
              for (let i = 0; i < response.data.dataList.length; i++)
                response.data.dataList[i]['type'] = 'CLKN';
            }
            _this[key] = response.data.dataList;
            _this.total = response.data.total;
          }
        });
      },
      dataSearchData (arg, param) {
        return this.axios.post(this.conf['url'].concat('/api/dataSearch/searchData').concat(!!param ? param : ''), arg)
      },
      Exists() {},
      removeAll() {},
      closeSource() {
        this.showSource = false;
        if(this.selectTemp.length == 0){
          if(!!this.$refs.clkj_tree.getTreeRef()){
            let checked = this.$refs.clkj_tree.getTreeRef().getCheckedNodes();
            for (let i = 0; i < checked.length; i++) {
              this.$refs.clkj_tree.getTreeRef().setChecked(checked[i][this.defaultProps.id], false);
            }
          }
          if(!!this.$refs.clkn_tree.getTreeRef()){
            let checked2 = this.$refs.clkn_tree.getTreeRef().getCheckedNodes();
            for (let j = 0; j < checked2.length; j++) {
              this.$refs.clkn_tree.getTreeRef().setChecked(checked2[j][this.defaultProps.id], false);
            }
          }
        }else{

          let content_types = '';
          for (let m = 0; m < this.selectTemp.length; m++) {
            if(content_types.indexOf(this.selectTemp[m].type) == -1)
              content_types += this.selectTemp[m].type;
          }
          //reset
          if(!!this.$refs.clkj_tree.getTreeRef()){
            if(content_types.indexOf('CLKJ') > -1)
              for (let l = 0; l < this.data.length; l++)
                this.$refs.clkj_tree.getTreeRef().setChecked(this.data[l]['source_code'], false);
          }

          if(!!this.$refs.clkn_tree.getTreeRef()){
            if(content_types.indexOf('CLKN') > -1)
              for (let l = 0; l < this.newsData.length; l++)
                this.$refs.clkn_tree.getTreeRef().setChecked(this.newsData[l]['source_code'], false);
          }

          for (let k = 0; k < this.selectTemp.length; k++) {
            if(this.selectTemp[k].type.toUpperCase() == 'CLKJ'){
              if(!!this.$refs.clkj_tree.getTreeRef()){
                this.$refs.clkj_tree.getTreeRef().setChecked(this.selectTemp[k].id, this.selectTemp[k].checked);
              }
            }else {
              if(!!this.$refs.clkn_tree.getTreeRef()){
                this.$refs.clkn_tree.getTreeRef().setChecked(this.selectTemp[k].id, this.selectTemp[k].checked);
              }
            }
          }
        }
      },
      clickConfirm() {
        this.$emit("getSelectItem", this.selectData, document.querySelector('.dialog-container2 [tabindex="0"]').getAttribute('id'));
        this.showSource = false;
      },
      currentChange(val, id) {
        this.curPage = val;
        if(document.querySelector('.dialog-container2 [tabindex="0"]').getAttribute('id') == 'tab-clkn-popup')
          this.paperinfo();
        else
          this.infoinit();
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
              checked: true,
              type: this.activeName == 'clkj-popup' ? 'CLKJ' : 'CLKN'
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
            this.$refs.clkj_tree.getTreeRef().setChecked(item['id'], false, false);
            this.$refs.clkn_tree.getTreeRef().setChecked(item['id'], false, false);
            return;
          };
        }
      },
      clearSelectData(){
        let _this = this;
        let arr_0 = this.$refs.clkj_tree.getTreeRef().getCheckedNodes();
        let arr_1 = this.$refs.clkn_tree.getTreeRef().getCheckedNodes();
        arr_0.forEach(e => {
          _this.$refs.clkj_tree.getTreeRef().setChecked(e[_this.defaultProps.id], false);
        });
        arr_1.forEach(e => {
          _this.$refs.clkn_tree.getTreeRef().setChecked(e[_this.defaultProps.id], false);
        });
        this.selectData = [];
      },
    }
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