<template>
  <div class="gray_bg">
    <div class="wrapper clearfix">
      <div class="sidebar">
        <!-- 学科导航 -->
        <div class="wbor bor_top">
          <h4>
            <span class="fl cls_group_title" @click="toggleGroupTreeStatus(0)"><img src="@@/clkd/images/icon_tit01.png"/>学科领域</span>
            <span class="fr"><a :class="cls_open_states[0] ? 'arrowon' : 'arrow'" type="extend" @click="toggleGroupTreeStatus(0)"></a></span>
          </h4>
          <div class="adv_flex" v-show="cls_open_states[0]">
            <div v-if="caseFlag" :class="reason_closed?'cls_cur1':'cls_cur0'">
              <a @click="changeCLs('1')">案由导航</a>
            </div>
            <div v-if="orgFlag" :class="office_closed?'cls_cur1':'cls_cur0'">
              <a @click="changeCLs('2')">发布机关</a>
            </div>
            <div v-if="konwFlag" :class="knowledge_closed?'cls_cur1':'cls_cur0'">
              <a @click="changeCLs('3')">知识导航</a>
            </div>
          </div>
          <div class="adv_flex_r" v-show="cls_open_states[0]">
            <span class="clear_chose" @click="checkAll">全选</span>
            <span class="clear_chose" @click="clearAll">清除</span>
          </div>
          <!-- 学科导航 -->
          <span v-show="false" class="public-cond-item" bind_name="subjectConds" type="items"></span>
          <div class="tree p_right30 _sub_tree" v-show="cls_open_states[0]">
            <urtp-tree :load="reason_loadNode" :dataModel="dataModel" lazy @check-change="reason_handleCheckChange"
              v-show="reason_closed" check-strictly ref="reasonTree" highlight-current :props="defaultProps">
            </urtp-tree>
            <urtp-tree :load="office_loadNode" :dataModel="dataModel" lazy @check-change="office_handleCheckChange"
              v-show="office_closed" check-strictly ref="officeTree" highlight-current :props="defaultProps">
            </urtp-tree>
            <urtp-tree :load="knowledge_loadNode" :dataModel="dataModel" lazy @check-change="knowledge_handleCheckChange"
              v-show="knowledge_closed" check-strictly ref="knowledgeTree" highlight-current :props="defaultProps">
            </urtp-tree>
          </div>
          <div class="clear"></div>
        </div>
        <!-- cls_group -->
        <div v-loading="group_loading" type="items" class="result-cond-item" bind_name="groupConds">
          <div class="wbor cls_group source_group" :key="'__source_group'" v-show="sourceGroup.length>0">
            <h4>
              <span class="fl"><a @click="toggleGroupTreeStatus(1)" class="cls_group_title">资源类型</a></span>
              <span class="fr"><a :class="cls_open_states[1] ? 'arrowon' : 'arrow'" type="extend" @click="toggleGroupTreeStatus(1)"></a></span>
            </h4>
            <ul class="tree p_right30" v-show="cls_open_states[1]">
              <li v-for="(g,i) in sourceGroup" :key="i">
                <span class="_group_cls_num">({{ g.total }})</span>
                <span class="treeL"><input type="checkbox" v-model="source_group_input_checked[i]" @change="sourceChangeSearch"/></span>
                <label><a @click="bindSourceChangeSearch($event)" :value="g.tableId">{{ g.source }}</a></label>
              </li>
            </ul>
            <div class="clear"></div>
          </div>
          <div class="wbor cls_group" :field="item['field']" v-for="(item,i) in groups" v-show="(item.data!=null)&&(item.data.length>0)" :key="i">
            <h4>
              <span class="fl"><a @click="toggleGroupTreeStatus((sourceGroup.length>0 ? 2 : 1) + i)" class="cls_group_title"><img :src="item.icon"/>{{ item.title }}</a></span>
              <span class="fr"><a :class="cls_open_states[(sourceGroup.length>0 ? 2 : 1) + i] ? 'arrowon' : 'arrow'" type="extend" @click="toggleGroupTreeStatus((sourceGroup.length>0 ? 2 : 1) + i)"></a></span>
            </h4>
            <ul class="tree p_right30" v-show="cls_open_states[(sourceGroup.length>0 ? 2 : 1) + i]">
              <li v-for="(g,i) in item.data" v-if="!(((item['field'].indexOf('year') == -1 && item['field'].indexOf('keywords') == -1 && item['field'].indexOf('xl_level') == -1) && (g.Code == g.Name || g.Name == '')) || (g.Code == '' || g.Name == ''))" :key="i">
                <span class="_group_cls_num">({{ g.Count }})</span>
                <span class="treeL"><input type="checkbox" @change="groupSearch"/></span>
                <label><a :value="g.Code" @click="bindGroupSearch($event)">{{ (item['field'] == 'author_code' && g.Name.indexOf(',') > -1) ? g.Name.split(',')[0] : g.Name }}</a></label> <!-- class="blue"-->
              </li>
            </ul>
            <div class="clear"></div>
          </div>
        </div>
        <!-- 检索历史 暂时隐藏 -->
        <div class="wbor search_history" v-if="searchHistory.length > 0 && false">
          <h4>
            <span class="fl"><a @click="toggleGroupTreeStatus(-1, 'h')" class="cls_group_title"><img src="@@/clkd/images/icon_tit10.png"/>检索历史</a></span>
            <span class="fr"><a class="arrow" type="extend" @click="toggleGroupTreeStatus(-1, 'h')"></a></span>
          </h4>
          <ul class="tree p_right30" v-show="isShowHistoryButton">
            <li
              v-for="(item, index) in (isAllHistory ? searchHistory : (searchHistory.length > 2 ? [searchHistory[0], searchHistory[1]] : searchHistory))" :key="index">
                <span class="treeL">
                  <input name="" type="checkbox" :checked="index ? false : true"/>
                </span>
              <label><a class="blue">{{ item.word }}</a></label>
            </li>
          </ul>
          <div class="fr delete" v-show="isShowHistoryButton">
            <span @click="historyClear"><a class="red">清空</a></span>
            <span><a class="green" @click="showAllHistory">全部搜索记录</a></span>
          </div>
          <div class="clear"></div>
        </div>
      </div>
      <navi-resource-switch :rightTagArray="classGroupTag" :dropMenuItem="dropArray" :_title="menutitle" @changeView="changeView">
      </navi-resource-switch>
      <div class="content_w c_wbor content_fg search_area">
        <div class="c_tit titlw">
          <a v-if="isSupport('adv')" :class="srchbox_type=='adv'?'c_titon':''" @click="changeSrchbox('adv')"> 高级检索</a>
          <a v-if="isSupport('sentence')" :class="srchbox_type=='sentence'?'c_titon':''" @click="changeSrchbox('sentence')">句子检索</a>
          <a v-if="isSupport('major')" :class="srchbox_type=='major'?'c_titon':''" @click="changeSrchbox('major')">专业检索</a>
        </div>
        <router-view @search_event="search_event" @sentence_search_event="sentence_search_event" @itemsUpdate="itemsUpdate"
                       @itemsRemove="itemsRemove" @initTableId="initTableId" :key="routerKey"></router-view>
      </div>
      <result-content ref="content" @itemsUpdate="itemsUpdate" @groupChange="groupChange"
                  @itemsChange="itemsChange" @initFinish="initFinish" @re_clsGroup_event="re_clsGroup_event" @resultSearch="resultSearch"
                  :_pageSize="query.pageSize" :_groupConds="groupConds" :_resultGroupItems="resultGroupItems" :_dataList="listInfos" :_tableId="tableId0"
                  :_contentType="contentType" :_loading="search_loading"></result-content>
    </div>
  </div>
</template>
<script>
  import "@@/clkd/css/index.css"
  import "@@/clkd/css/advsearch.css"
  import "@@/clkd/css/common.css"
  import {naviResourceSwitch} from '@/components/law8/clkd8/search/index'
  import buildVModelTools from "@/mixins/buildVModelTools";
  import clkd8 from "@/mixins/clkd8";
  import validate from "@/mixins/validate";
  import {getPath} from "../../common/filehelper";

  let group = Vue.prototype.group;
  let productCode = Vue.prototype.productCode;

  export default {
    mixins: [buildVModelTools, clkd8, validate],
    components: {
      naviResourceSwitch,
      ResultContent: () =>
        import(`@/skin/${getPath("result", group, productCode, "_result_content")}`),
    },
    data() {
      const imgDir = 'static/clkd/images/';
      return {
        srchbox_type: localStorage.getItem('search_type') ? localStorage.getItem('search_type') : 'advsearch',
        groups: [],
        sourceGroup: [],
        data: [],
        classGroupTag: [
          {title: '期刊', code: 'cjfq', tableId: '537'},
          {title: '博士', code: 'cdmd', tableId: '539'},
          {title: '硕士', code: 'cdmd', tableId: '540'},
          {title: '会议', code: 'cipd', tableId: '542'},
          {title: '报纸', code: 'ccnd', tableId: '544'},
          {title: '大陆法规单篇', code: 'clklp', tableId: '609'},
          {title: '香港法规单篇',  code: 'clklp', tableId: '611'},
          {title: '大陆法规条目',  code: 'clklt', tableId: '610'},
          {title: '香港法规条目',  code: 'clklt', tableId: '612'},
          {title: '案例', code: 'clkc', tableId: '608'},
          {title: '合同', code: 'clkhp', tableId: '607'},
          {title: '合同条款', code: 'clkht', tableId: '623'},
          {title: '课程',  code: 'vedio', tableId: '604'},
          {title: '年鉴',  code: 'cyfd', tableId: '4721'},
          {title: '专利',  code: 'scod', tableId: '4722,4723'},
          {title: '标准',  code: 'cisd', tableId: '4724,4725'},
          {title: '学术观点', code: 'point', tableId: '605'},
          {title: '工具书', code: 'crfd', tableId: '4726'},
          {title: '全部',  code: 'scdb', tableId: ''},
        ],
        dropArray: [],
        qModel: undefined,//是否在该基础上构建条件
        query: {
          productCode: 'clkd8',
          sysId: '113',
          sType: '0',
          resId: '',
          tableId: '',
          conds: {},
          sentenceConds: [],
          order: [],
          date: [],
          pageNum: 1,
          pageSize: 20,
          total: 0,
          callbacks: null
        },
        contentType: 'scdb',
        listInfos: {},
        groupConds: [],
        resultGroupItems: [],
        subjectConds: [],
        isAllHistory: false,
        searchHistory: [{'word': '数据法学'}, {'word': '人工智能'}],
        isShowHistoryButton: true,
        cls_open_num: 2,//默认展开导航数
        cls_open_states: [],//控制学科、来源、分组项、检索历史是否展示  拆开更加合理一点
        tableId0: '',//用于还原资源tableId(来源分组点击检索动作逻辑)
        dataModel: {
          id_key: 'fld_sysid',
          label_key: 'class_name',
          value_key: 'class_code',
          grade_key: 'class_grade',
          isHaveChildren_key: 'child_flag',
          children_key: 'children'
        },
        defaultProps: {
          keyField: 'sys_code',
          label: 'class_name',
          children: 'children',
          isLeaf: 'leaf'
        },
        caseFlag: 0,
        orgFlag: 0,
        konwFlag: 1,
        reason_closed: false,
        office_closed: false,
        knowledge_closed: true,
        caseReason_tree: [],
        office_tree: [],
        knowledge_tree: [],
        caseReason_clsTableId: '4682',// 案由
        office_clsTableId: '4683',// 发布机关
        knowledge_clsTableId: '4684',// 知识
        source_group_input_checked: [false, false, false, false, false],
        search_loading: false,
        group_loading: false,
        public_validate_item: ['empty', 'greater120', 'specialCode', 'dateValidate', 'clsValidate'],
        //当前产品的资源代码和tableid
        proinfo: {
          code: 'scdb',
          tableId: '',
        },
        menutitle: localStorage.getItem("title")?localStorage.getItem("title"):"法律总库",
        group_special_handler: function (queryModel, key){
          let model;
          if(key.indexOf('year') > -1){
            queryModel.setOrder('year', 'desc');
            model = queryModel.toJson();
          }else if(key == 'xl_level'){
            queryModel.newSrchItem('tmf', 'N');
            model = queryModel.toJson();
          }
          else{
            model = queryModel.toJson();
            //移除order
            if(model.hasOwnProperty('order'));
            delete model.order;
          }
          return model;
        },
        cacheKey: '',
      };
    },
    computed: {
  	  // 路由变换时, 方法执行
	    routerKey() {
        let flag =  this.$route.path.split('/')[2];
        this.cacheKey = flag;
        return flag;
      },
    },
    watch: {
      cacheKey() {
        if(this.$route.path.includes('clkl') || this.$route.path.includes('clklp') || this.$route.path.includes('clklt')){
          this.konwFlag=1;
          this.caseFlag=0;
          this.orgFlag=1;
          this.changeCLs('2');
        }else if(this.$route.path.includes('clkc')){
          this.konwFlag=1;
          this.caseFlag=1;
          this.orgFlag=0;
          this.changeCLs('1');
        }else {
          this.konwFlag=1;
          this.caseFlag=0;
          this.orgFlag=0;
          this.changeCLs('3');
        }
      },
      caseReason_tree(){
        this.$refs.reasonTree.getTreeRef().setCheckedNodes(this.caseReason_tree);
      },
      office_tree(){
        this.$refs.officeTree.getTreeRef().setCheckedNodes(this.office_tree);
      },
      knowledge_closed(){
        this.$refs.knowledgeTree.getTreeRef().setCheckedNodes(this.knowledge_closed);
      },
    },
    created() {
      this.classGroupTag.splice(this.classGroupTag.length - 1, 1, {title: '全部', path: '', code: 'scdb', tableId: this.SCDB});
      for (let i = 0; i < this.cls_open_num; i++)
        this.cls_open_states.push(this.cls_open_num > 0);

      this.tableId0 = this.getTableId0(this.$route.meta.resCode);
      this.menutitle = this.getTitle(this.$route.meta.resCode);
      this.setSrcboxChecked(this.$route.fullPath);

      const introductions = require.context('@/skin/advsearch/law8/clkd8/', true, /\.vue$/); // 返回值是函数
      this.support_srcbox_type = introductions.keys();

      let columns= [{key:'论文库',code:'paper',nodes:['期刊','博士','硕士','会议','报纸']},
        {key:'法律法规库',code:'law',nodes:['大陆法规单篇','香港法规单篇','大陆法规条目','香港法规条目']},
        {nodes:['案例','合同','合同条款','课程']},
        {nodes:['年鉴','专利','标准','学术观点']}];
      let _this=this;
      _this.dropArray=[];
      columns.forEach(function(item){
        let obj={}
        if(!!item.key)obj['key']=item.key;
        if(!!item.code)
        {
          obj['code']=item.code;
          obj['path']='/advsearch/'+item.code+'/adv';
        }
        if(item.nodes.length>0)
        {
          let tableids=[];
          let ary =[];
          item.nodes.forEach(function(node){    //todo wcc 两层遍历+一个filter，复杂度变高了，考虑优化数据结构
            const temp=_this.classGroupTag.filter(
              function(t){
                return t['title']==node
              }
            );
            ary.push(temp[0])
            tableids.concat(temp[0].tableId)
          })
          obj['content']=ary;
          obj['tableId']=tableids.join(',');
          _this.dropArray.push(obj);
        }
      })
    },
    mounted() {
      this.bindEnterEvent();
      if(this.$route.name == 'advsearch')
        this.redirectSCDB();
    },
    methods: {
      isSupport(suffix){
        let prefix = './_searchbox', middle = '_' + this.$route.meta.resCode;
        suffix = '_' + suffix + '.vue';
        return this.support_srcbox_type.indexOf(prefix + middle + suffix) > -1;
      },
      redirectSCDB(){
        this.menutitle = '法律总库';
        this.$router.push({
          path: '/advsearch/scdb/adv',
        });
        this.proinfo = {
          code: 'scdb',
          tableId: this.SCDB
        }
      },
      getTableId0(code){
        for (let i = 0; i < this.classGroupTag.length; i++) {
          if(this.classGroupTag[i].code == code)
            return this.classGroupTag[i].tableId;
        }
      },
      getTitle(code){
        for (let i = 0; i < this.classGroupTag.length; i++) {
          if(this.classGroupTag[i].code == code)
            return this.classGroupTag[i].title;
        }
      },
      setSrcboxChecked(path){
        if(!path){
          this.srchbox_type = 'adv';
          return;
        }
        let type = path.split('/');
        if(type.length > 0)
          type = type[type.length - 1];
        else
          type = 'adv';
        this.srchbox_type = type;
      },
      checkAll() {
        if(this.reason_closed)
          this.selectAll(this.$refs.reasonTree.getTreeRef().root);
        if(this.office_closed)
          this.selectAll(this.$refs.officeTree.getTreeRef().root);
        if(this.knowledge_closed)
          this.selectAll(this.$refs.knowledgeTree.getTreeRef().root);
      },
      clearAll(){
        if(this.reason_closed)
          this.$refs.reasonTree.getTreeRef().setCheckedNodes([]);
        if(this.office_closed)
          this.$refs.officeTree.getTreeRef().setCheckedNodes([]);
        if(this.knowledge_closed)
          this.$refs.knowledgeTree.getTreeRef().setCheckedNodes([]);
      },
      resetTree(){
        this.subjectConds = [];
        //全部折叠
        this.foldAll(this.$refs.reasonTree.getTreeRef().root);
        this.foldAll(this.$refs.officeTree.getTreeRef().root);
        this.foldAll(this.$refs.knowledgeTree.getTreeRef().root);
        if(this.reason_closed){
          let checkeds = this.$refs.reasonTree.getTreeRef().getCheckedNodes();
          for (let i = 0; i < checkeds.length; i++) {
            this.$refs.reasonTree.getTreeRef().setChecked(checkeds[i].fld_sysid, false);
          }
          let root = this.$refs.reasonTree.getTreeRef().root.childNodes;
          for (let j = 0; j < root.length; j++) {
            this.$refs.reasonTree.getTreeRef().setChecked(root[j].data.fld_sysid, true);
          }
        };
        if(this.office_closed){
          let checkeds = this.$refs.officeTree.getTreeRef().getCheckedNodes();
          for (let i = 0; i < checkeds.length; i++) {
            this.$refs.officeTree.getTreeRef().setChecked(checkeds[i].fld_sysid, false);
          }
          let root = this.$refs.officeTree.getTreeRef().root.childNodes;
          for (let j = 0; j < root.length; j++) {
            this.$refs.officeTree.getTreeRef().setChecked(root[j].data.fld_sysid, true);
          }
        };
        if(this.knowledge_closed){
          let checkeds = this.$refs.knowledgeTree.getTreeRef().getCheckedNodes();
          for (let i = 0; i < checkeds.length; i++) {
            this.$refs.knowledgeTree.getTreeRef().setChecked(checkeds[i].fld_sysid, false);
          }
          let root = this.$refs.knowledgeTree.getTreeRef().root.childNodes;
          for (let j = 0; j < root.length; j++) {
            this.$refs.knowledgeTree.getTreeRef().setChecked(root[j].data.fld_sysid, true);
          }
        };
      },
      initShowGrade(){
        this.contentType = this.$route.meta.resCode;
      },
      changeView(path, code, tableId) {
        this.menutitle = localStorage.getItem("title");
        this.$router.push({
          path: '/advsearch/'+code+'/'+'adv',
        });
        this.proinfo = {
          code: code,
          tableId: tableId
        }
      },
      changeCLs(flag) {
        if((flag == '1' && this.reason_closed) || (flag == '2' && this.office_closed) || (flag == '3' && this.knowledge_closed))
          return;
        if (flag == '1') {
          this.reason_closed = true;
          this.office_closed = false;
          this.knowledge_closed = false;
        }
        if (flag == '2') {
          this.reason_closed = false;
          this.office_closed = true;
          this.knowledge_closed = false;
        }
        if (flag == '3') {
          this.reason_closed = false;
          this.office_closed = false;
          this.knowledge_closed = true;
        }
        this.resetTree();
      },
      toggleGroupTreeStatus(index, isHistory) {
        if(index > -1)
          this.cls_open_states.splice(index, 1, !this.cls_open_states[index]);

        if(!!isHistory)
          this.isShowHistoryButton = !this.isShowHistoryButton;
      },
      //TODO
      initFinish() {
        this.groupConds = [];
        if (this.contentType == 'scdb') {
          this.resultGroupItems = [
            {Name: '', 'tableId': this.SCDB}
          ];
        } else if (this.contentType == 'paper') {
          this.resultGroupItems = [
            {Name: '论文库', 'tableId': '537,539,540,542,544'},
            {Name: '学术期刊', 'tableId': '537'},
            {Name: '博士论文', 'tableId': '539'},
            {Name: '硕士论文', 'tableId': '540'},
            {Name: '会议论文', 'tableId': '542'},
            {Name: '报纸文献', 'tableId': '544'},
          ];
        } else if (this.contentType == 'law') {
          this.resultGroupItems = [
            {Name: '法律法规库', 'tableId': '609,611'},
            {Name: '中国大陆法规', 'tableId': '609'},
            {Name: '中国香港法规', 'tableId': '611'}
          ];
        } else if (this.contentType == '4') {
          this.resultGroupItems = [
            {Name: '', 'tableId': '608'},
          ];
        }
      },
      initTableId(tableId){
        this.itemsUpdate("tableId",tableId);
        this.tableId0 = tableId;
      },
      search_event(qModel, type) {
        let result = this.validate(type, type == 'professional' ? this.public_validate_item.concat(['majorValidate']) : this.public_validate_item);
        if(!result.status){
          this.$alert(result.msg, '提示信息', {
            confirmButtonText: '确定',
            callback: action => {}
          })
          return;
        }

        this.initShowGrade();
        this.groupConds = [];
        this.$nextTick(function () {
          this.qModel = qModel;
          let queryModel = this.buildModel();
          this.searchExecute(queryModel);
          //分组导航
          this.clsGroup_event(queryModel, this.group_special_handler);
          this.sourceGroup_event(queryModel);
        })
      },
      sentence_search_event(qModel) {
        let result = this.validate('sentence', this.public_validate_item);
        if(!result.status){
          this.$alert(result.msg, '提示信息', {
            confirmButtonText: '确定',
            callback: action => {}
          })
          return;
        }

        this.groupConds = [];
        this.$nextTick(function () {
          this.qModel = qModel;
          let queryModel = this.buildModel();
          this.searchExecute(queryModel, '?data_type=1&markRed=true&isLazy=true');
          //分组导航
          this.clsGroup_event(queryModel);
          this.sourceGroup_event(queryModel);
        })
      },
      groupSearch() {
        let groupItem, groupItems = [];
        document.querySelectorAll('.sidebar .cls_group').forEach(function (items) {
          groupItem = [];
          items.querySelectorAll('input:checked').forEach(function (item) {
            let field = item.parentNode.parentNode.parentNode.parentNode.getAttribute('field'),
              value = item.parentNode.parentNode.querySelector('a').getAttribute('value'),
              vName = item.parentNode.parentNode.querySelector('a').text,
              fName = item.parentNode.parentNode.parentNode.parentNode.querySelector('a').text;
            groupItem.push({'field': field, 'fName': fName, 'vName': vName, 'value': value, 'logic': 'OR'});
          });
          if (groupItem.length > 0)
            groupItems.push(groupItem);
        })
        this.groupConds = groupItems;
        this.$refs.content.resetSearch(true);
      },
      bindGroupSearch(event) {
        let e = event.target, input = e.parentNode.parentNode.querySelector('input');
        input.checked = !input.checked;
        this.groupSearch();
      },
      sourceChangeSearch(){
        let tableId = '';
        document.querySelectorAll('.sidebar .source_group input').forEach(function(item){
          if(item.checked)
            tableId += item.parentNode.parentNode.querySelector('a').getAttribute('value') + ',';
        })
        tableId = tableId==''?this.tableId0: tableId.substring(0, tableId.length - 1);
        this.itemsChange('tableId', tableId, false);
        this.groupSearch();
      },
      bindSourceChangeSearch(event){
        event.target.parentNode.parentNode.querySelector('input').checked = !event.target.parentNode.parentNode.querySelector('input').checked;
        this.sourceChangeSearch();
      },
      re_clsGroup_event(){
        this.clsGroup_event(this.buildModel());
      },
      historyClear() {
        this.searchHistory = [];
      },
      showAllHistory() {
        this.isAllHistory = true;
      },
      changeSrchbox(type){
        localStorage.removeItem('search_type');
        localStorage.setItem('search_type',type);
        this.srchbox_type = type;
        const path=this.$route.path.replace(/[^/]+(?!.*\/)/,type);
        this.$router.push({
          path: path
        });
      },
      groupChange(key, value){
        this[key] = value;
      },
      loadTree() {
        //学科领域
        let _this = this, queryModel = new this.$queryModel();
        queryModel.setTableId(this.caseReason_clsTableId);
        queryModel.setSysId(this.query.sysId);
        queryModel.newSrchItem('class_grade', '1');
        this.API.getClsData(queryModel.toJson()).then(function (response) {
          let clsData = response.data;
          if (clsData.code == '200' && clsData.dataList.length > 0)
            _this.data = clsData.dataList;
        })
      },
      reason_loadNode(node, resolve) {
        if (node.level === 0) {
          let queryModel = new this.$queryModel();
          queryModel.setTableId(this.caseReason_clsTableId);
          queryModel.setSysId(this.query.sysId);
          queryModel.newSrchItem('class_grade', '1');
          let this_ = this;
          this.API.getClsData(queryModel.toJson()).then(function (res) {
            if (res.data && res.data.dataList) {
              let arr = res.data.dataList;
              arr.forEach(e => {
                if (e.child_flag == '1') {
                  e.children = [];
                }
              })
              this_.caseReason_tree = arr;
              return resolve(arr);
            }
          })
        }
        if (node.level > 0) {
          if (!!node.data['children'] && node.data['children'].length > 0) {
            return resolve(node.data['children']);
          } else {
            let queryModel = new this.$queryModel();
            queryModel.setSysId(this.query.sysId);
            queryModel.setTableId(this.caseReason_clsTableId);
            queryModel.newSrchItem('class_grade', parseInt(node.data['class_grade']) + 1, 'AND');
            queryModel.newSrchItem('syscode', node.data['sys_code'], 'AND');
            this.API.getClsData(queryModel.toJson()).then(function (res) {
              node.data['children'] = res.data.dataList;
              return resolve(res.data.dataList);
            });
          }
        }
      },
      office_loadNode(node, resolve) {
        if (node.level === 0) {
          let queryModel = new this.$queryModel();
          queryModel.setTableId(this.office_clsTableId);
          queryModel.setSysId(this.query.sysId);
          queryModel.newSrchItem('class_grade', '1');
          let this_ = this;
          this.API.getClsData(queryModel.toJson()).then(function (res) {
            if (res.data && res.data.dataList) {
              let arr = res.data.dataList;
              arr.forEach(e => {
                if (e.child_flag == '1') {
                  e.children = [];
                }
              })
              this_.office_tree = arr;
              return resolve(arr);
            }
          })
        }
        if (node.level > 0) {
          if (!!node.data['children'] && node.data['children'].length > 0) {
            return resolve(node.data['children']);
          } else {
            let queryModel = new this.$queryModel();
            queryModel.setSysId(this.query.sysId);
            queryModel.setTableId(this.office_clsTableId);
            queryModel.newSrchItem('class_grade', parseInt(node.data['class_grade']) + 1, 'AND');
            queryModel.newSrchItem('syscode', node.data['sys_code'], 'AND');
            this.API.getClsData(queryModel.toJson()).then(function (res) {
              node.data['children'] = res.data.dataList;
              return resolve(res.data.dataList);
            });
          }
        }
      },
      knowledge_loadNode(node, resolve) {
        if (node.level === 0) {
          let queryModel = new this.$queryModel();
          queryModel.setTableId(this.knowledge_clsTableId);
          queryModel.setSysId(this.query.sysId);
          queryModel.newSrchItem('class_grade', '1');
          let this_ = this;
          this.API.getClsData(queryModel.toJson()).then(function (res) {
            if (res.data && res.data.dataList) {
              let arr = res.data.dataList;
              arr.forEach(e => {
                if (e.child_flag == '1') {
                  e.children = [];
                }
              })
              this_.knowledge_closed = arr;
              return resolve(arr);
            }
          })
        }
        if (node.level > 0) {
          if (!!node.data['children'] && node.data['children'].length > 0) {
            return resolve(node.data['children']);
          } else {
            let queryModel = new this.$queryModel();
            queryModel.setSysId(this.query.sysId);
            queryModel.setTableId(this.knowledge_clsTableId);
            queryModel.newSrchItem('class_grade', parseInt(node.data['class_grade']) + 1, 'AND');
            queryModel.newSrchItem('syscode', node.data['sys_code'], 'AND');
            this.API.getClsData(queryModel.toJson()).then(function (res) {
              node.data['children'] = res.data.dataList;
              return resolve(res.data.dataList);
            });
          }
        }
      },
      reason_handleCheckChange() {
        let items = [];
        this.buildTreeConditions('reasonTree', items, 'case_code');
        this.subjectConds = !this.isAllChecked('reasonTree', items) ? items : [];
      },
      office_handleCheckChange() {
        let items = [];
        this.buildTreeConditions('officeTree', items, 'org_code');
        this.subjectConds = !this.isAllChecked('officeTree', items) ? items : [];
      },
      knowledge_handleCheckChange() {
        let items = [];
        this.buildTreeConditions('knowledgeTree', items, 'zt_subcode');
        this.subjectConds = !this.isAllChecked('knowledgeTree', items) ? items : [];
      },
      isAllChecked(treeRef){
        let flag = true, checked_nodes = [], root = this.$refs[treeRef].getTreeRef().root.childNodes, root_nodes = [];
        for (let j = 0; j < root.length; j++) {
          root_nodes.push(root[j].data[this.defaultProps.keyField]);
        }
        if(!!this.$refs[treeRef].getTreeRef()){
          checked_nodes = this.$refs[treeRef].getTreeRef().getCheckedNodes();
          if(checked_nodes.length == 0)
            return !flag;
          for (let i = 0; i < checked_nodes.length; i++) {
            if(root_nodes.length != checked_nodes.length){
              flag = false;
              break;
            }else {
              if(root_nodes.indexOf(checked_nodes[i][this.defaultProps.keyField]) == -1){
                flag = false;
                break;
              }
            }
          }
        }
        return flag;
      },
      buildTreeConditions(treeRef, items, field){
        let checked_nodes = this.$refs[treeRef].getTreeRef().getCheckedNodes();
        checked_nodes.forEach((item) => {
          if(!!item.children && item.children.length > 0){
            //未选中子节点
            if(this.getSelectedChildNum(treeRef, item.children).length == 0){
              items.push({
                id: item.sys_code,
                field: field,
                value: item.class_code,
                logic: 'OR',
                name: item['class_name'],
                ignore: true
              });
            }
          } else{
            items.push({
              id: item.sys_code,
              field: field,
              value: item.class_code,
              logic: 'OR',
              name: item['class_name'],
              ignore: true
            });
          }
        })
      },
      getSelectedChildNum(treeRef, children){
        let checkeds = [], all_checked_nodes = this.$refs[treeRef].getTreeRef().getCheckedNodes();
        for (let i = 0; i < children.length; i++) {
          for (let j = 0; j < all_checked_nodes.length; j++) {
            if(all_checked_nodes[j]['sys_code'] == children[i]['sys_code']){
              checkeds.push(children[i]);
              break;
            }
          }
        }
        return checkeds;
      },
    },
  };
</script>

<style scoped>
.content_w:nth-child(3) {margin-top: 20px;}
.p_right30 {overflow: auto;max-height: 408px;}
._sub_tree {padding: 5px 15px;}
.cls_group_title {cursor: pointer;}
.cls_cur0 {color: #BEBEBE;margin-left: 20px;cursor: pointer;}
.cls_cur1 {color: #1b9cff;margin-left: 20px;cursor: pointer;}
.c_result{width: 79%;float: right;background-color: #fff;}
</style>
