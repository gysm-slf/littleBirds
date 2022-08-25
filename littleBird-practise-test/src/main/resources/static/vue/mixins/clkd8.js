import {encrypt} from "urtp-aes-encrypt";

export default {
  data() {
    return {
      query: {
        sysId: '113'
      },
      centerType: 'mirror',
      mirrorZwjUrl: '',
      zwjUrl: '',
      sourceTypeMatchStatus: false,
      fundMatchStatus: false,
      action_bar_type: 'download,quote',
      updateTimeOption: [{
        value: '',
        label: '不限'
      }, {
        value: '最近一周',
        label: '最近一周'
      }, {
        value: '最近一月',
        label: '最近一月'
      }, {
        value: '最近半年',
        label: '最近半年'
      }, {
        value: '今年迄今',
        label: '今年迄今'
      }, {
        value: '上一年度',
        label: '上一年度'
      }],
    }
  },
  methods: {
    getFund_info(sysId, cust_key, where_format, limit){
      let _this = this, results = {};
      this.API.getCustomDataTable(sysId, cust_key, where_format, limit).then(function (response){
        if(response.data.code == '200'){
          results.dataList = response.data.dataList;
          results.total = response.data.total;
          _this.fund_results = results;
        }
      });
    },
    getSource_info(queryModel, key){
      let _this = this, results = {};
      this.API.dataSearchData(queryModel.toJson()).then(function (response){
        if(response.data.code == '200'){
          results[key] = response.data.dataList;
          results.total = response.data.total;
          _this.source_results = results;
        }
      });
    },
    getSentenceNum(xmlstr) {
      let total = 0;
      //创建文档对象
      let parser = new DOMParser();
      let xmlDoc = parser.parseFromString(xmlstr, "text/xml");

      let ele = xmlDoc.getElementsByTagName('Text');
      if(!!ele && ele.length > 0){
        ele = ele[0];
        total = parseInt(ele.getAttribute('Total')) > 2 ? 2 : parseInt(ele.getAttribute('Total'));
      }
      return total;
    },
    //TODO  未完成  还需要额外调用接口判断是否首发
    parseSourceMarkcode(sourceCode){
      let explain;
      switch (sourceCode.toUpperCase()) {
        case 'P01' :
          explain = '北大核心';
          break;
        case 'P0209' :
          explain = 'CSSCI';
          break;
      }
      return explain;
    },
    deleteLast(str, symbol){
      return (str.substr(str.length - 1, 1)) == symbol ? str.substring(0, str.length - 1) : str;
    },
    select(event, index){
      let e = event.target || event, type = e.className;
      /**
       * 全选
       * statuteItems 选中状态数组
       * selectRow    选中的记录行
       * */
      if(typeof index == 'undefined'){
        for (let i = 0; i < this.statuteItems.length; i++)
          this.statuteItems.splice(i, 1 , e.checked);
        for (let j = 0; j < this.dataList.length; j++) {
          if(e.checked){
            if(!this.selectRow.hasOwnProperty(this.dataList[j]['filename']))
              this.selectRow[this.dataList[j]['filename']] = this.dataList[j];
          }else
          if(this.selectRow.hasOwnProperty(this.dataList[j]['filename']))
            delete this.selectRow[this.dataList[j]['filename']];
        }
      }else{
        this.statuteItems.splice(index, 1, e.checked);
        this.selectAllStatus = !(this.statuteItems.filter(function (item) {
          return !item;
        }).length > 0);
        if(e.checked){
          if(!this.selectRow.hasOwnProperty(this.dataList[index]['filename']))
            this.selectRow[this.dataList[index]['filename']] = this.dataList[index];
        }else
        if(this.selectRow.hasOwnProperty(this.dataList[index]['filename']))
          delete this.selectRow[this.dataList[index]['filename']];
      }
    },
    selectAll(treeData){
      this.changeTreeRoot(treeData, 'checked', true);
    },
    unSelectAll(treeData){
      this.changeTreeRoot(treeData, 'checked', false);
    },
    openAll(treeData){
      this.changeTreeRoot(treeData, 'expanded', true);
    },
    foldAll(treeData){
      this.changeTreeRoot(treeData, 'expanded', false);
    },
    changeTreeRoot(treeData, key, value){
      if(treeData instanceof Object && treeData.level == 0){
        treeData[key] = value;
        this.changeTreeRoot(treeData.childNodes, key, value);
      }
      if(treeData instanceof Array && treeData.length > 0){
        for (let i = 0; i < treeData.length; i++) {
          treeData[i][key] = value;
          if(!!treeData[i].childNodes && treeData[i].childNodes.length > 0)
            this.changeTreeRoot(treeData[i].childNodes, key, value);
        }
      }
    },
    addMaxLength() {
      // document.querySelectorAll('.content_fg input').forEach(function (item) {
      //   item.maxLength = 120;
      // })
    },
    changeMatchStatus(e, key) {
      e.target.setAttribute('operate', e.target.parentNode.parentNode.querySelector('[urtp-data-match]').value);
      this[key] = false;
    },
    bindEnterEvent() {
      $(document).unbind('keyup').on('keyup', '.senior_search_components input', this.enterSearch);
    },
    enterSearch(event) {
      let code = event.keyCode || event.which;
      if (code == 13)
        document.querySelector('#btnSearch').click();

      event.cancelBubble = true;
    },
    getFundSelectItem(itemArr) {
      let str = '', values = '';
      itemArr.forEach(e => {
        str += e.label + '+';
        values += e.id + '+';
      });
      this.fundItem = str.substr(0, str.length - 1);
      this.fundItem_items = [];

      document.querySelector('[field="' + this.searchComponents['adv_popPage_fund'][0]['fieldKey'] + '"]').setAttribute('operate', '=');
      this.fundMatchStatus = true;
      document.querySelectorAll('.fund_pop_match option')[0].selected = true
      this.$nextTick(function () {
        this.fundItem_items = [{
          field: this.searchComponents['adv_popPage_fund'][0]['fieldKey'],
          value: values.substr(0, values.length - 1).replaceAll('+', ' + '),
          logic: 'OR',
          operate: document.querySelector('[field="' + this.searchComponents['adv_popPage_fund'][0]['fieldKey'] + '"]').getAttribute('operate'),
        }]
      })
    },
    /**
     * @param type          0:镜像(默认)  1:网页
     * @param code          tablename前4位
     * @param tableName     tablename
     * @param fileName      filename
     */
    getDetailUrl(code, tablename, filename) {
      if (!this.centerType) this.centerType = 'mirror';
      let baseUrl = this.type.toUpperCase() == 'MIRROR' ? this.mirrorZwjUrl
        : this.zwjUrl;
      code = code.toUpperCase() == 'ALCK' ? 'CLKC' : code;
      switch (this.centerType.toUpperCase()) {
        case 'MIRROR' :
          if (code == 'CLKJ' || code == 'CLKB' || code == 'CLKM' || code == 'CLKP' || code == 'CLKN' || code == 'CLKC') {
            baseUrl += "?code=" + filename + "&type=" + code + "TOTAL";
          } else if (code == 'CLKL') {
            if (filename.indexOf('Y') > -1) {
              baseUrl += "?code=" + filename + "&type=CLKLKLAST&hide=1";
            } else {
              baseUrl += "?code=" + filename + "&type=CLKLKLAST";
            }
          }
          break;
        case 'CENTER' :
          baseUrl += '?Dbcode=' + code + '&DbName=' + tablename + '&FileName=' + filename;
          break;
      }
      return baseUrl;
    },
    getZwjUrl(tablename, filename) {
      let source = tablename.substr(0, 4).toUpperCase(), params = {
        sysid: this.query.sysId
      };
      //博硕
      if (source == 'CLKB' || source == 'CLKM')
        params['resid'] = '2';

      //会议
      if (source == 'CLKP')
        params['resid'] = '3';

      //报纸
      if (source == 'CLKN')
        params['resid'] = '4';

      //期刊
      if (source == 'CLKJ')
        params['resid'] = '20';

      //法规单篇
      if (source == 'CLKL' || source == 'CLKK')
        params['resid'] = '33';

      //案例
      if (source == 'CLKC')
        params['resid'] = '17';

      params['filename'] = filename;
      // this.commonJump('/detail', params);
      // todo wcc 20220621
      this.commonJump('/litdetail', params);
    },
    commonJump(path, params) {
      window.open(this.$router.resolve({
        path: path,
        query: params
      }).href, '_blank');
    },
    /**
     * 期刊导航(某一期)
     * @param sysId
     * @param params
     *          {
     *            sysid,
     *            resid,
     *            pykm,
     *            year,
     *            isssue
     *          }
     */
    toPeriodicalCls(sysId, params) {
      let query = {
        'sysid': sysId,
        'resid': this.resourceUtils.ID_CJFQ
      }
      let keys = Object.keys(params);
      for (let i = 0; i < keys.length; i++)
        query[keys[i]] = params[keys[i]];
      this.commonJump('/pubdetail', query);
    },
    toKeywordsDetail(sysId, keywords) {
      this.commonJump('/keyword', {
        sysid: sysId,
        kwname: encrypt(keywords)
      });
    },
    toAuthorDetail(sysId, authorCode, authorName) {
      this.commonJump('/author', {
        sysid: sysId,
        aucode: authorCode,
        auname: encrypt(authorName)
      });
    },
    toOrgDetail(sysId, orgCode, orgName) {
      this.commonJump('/inst', {
        sysid: sysId,
        incode: orgCode,
        inname: encrypt(orgName)
      });
    },
    hasSelected(treeRef){
      if(typeof this.$refs[treeRef].getTreeRef() == 'undefined' || typeof this.$refs[treeRef].getTreeRef().root == 'undefined')
        return false;
      let root_nodes = this.$refs[treeRef].getTreeRef().root.childNodes, flag = false;
      for (let i = 0; i < root_nodes.length; i++) {
        if(root_nodes[i].checked == true){
          flag = true;
          break;
        }
      }
      return flag;
    },
    rangeDateFocus(key, relKey) {
      this[key] = '';
      this[relKey] = '';
    },
    changeValidate(key) {
      if (!!this[key][0] && !!this[key][1] && this[key][0] > this[key][1]) {
        let t = this[key][0];
        this[key].splice(0, 1, new Date(this[key][1].toLocaleDateString()));
        this[key].splice(1, 1, new Date(t.toLocaleDateString()));
      }
    },
    selectDateChangeR(value, field, bind_name, relKey) {
      let date = [], items = [];
      switch (value) {
        case 'lastWeek' :
          date = $urtp.getLastWeek();
          break;
        case 'lastMonth' :
          date = $urtp.getLastMonth();
          break;
        case 'lastHalfYear' :
          date = $urtp.getLastHalfYear();
          break;
        case 'yearSoFar' :
          date = $urtp.getYearSoFar();
          break;
        case 'preYear' :
          date = $urtp.getPreYear();
          break;
        default :
          date = ['', ''];
      }
      items['logics'] = 'AND';
      //如果value为空 清空bind_items 否则初始化bind_items并清除关联项
      if(value == ''){
        items = [];
      }else{
        items.push({
          field: field,
          value: date[0],
          operate: '>='
        });
        items.push({
          field: field,
          value: date[1],
          operate: '<='
        });

        if(!!this[relKey]){
          this[relKey][0] = '';
          this[relKey][1] = '';
        }
        if(!!this[relKey + '_items'])
          this[relKey + '_items'] = [];
      }
      if(!!bind_name)
        this[bind_name] = items;
    },
    senior_search_event() {
      //清空所有条件,并重置tableId与页码
      this.$emit('itemsUpdate', ['conds', 'tableId', 'pageNum', 'sentenceConds'], [{}, '', 1, []]);
      this.date_completion('senior');
      this.$nextTick(function () {
        this.joinConditionItems('public');
        this.joinConditionItems('senior');
        this.$emit('search_event', null, 'senior');//search_event：arg1: 基于该queryModel基础上进行构建 arg2: 校验类型
      })
    },
    sentence_search_event() {
      this.date_completion('sentence');
      this.$nextTick(function () {
        this.joinSentenceItems();
        this.$emit('sentence_search_event');
      })
    },
    major_search_event() {
      this.$emit('itemsUpdate', ['conds', 'tableId', 'pageNum', 'sentenceConds'], [{}, '', 1, []]);
      this.date_completion('professional');
      this.$nextTick(function () {
        this.joinConditionItems('public');
        this.joinConditionItems('major');
        this.$emit('search_event', null, 'professional');
      })
    },
    date_completion(type){
      let date_ele = document.querySelectorAll('.' + type + '_search_components [type="date"]'), date = ['', ''], formart, _this = this;
      if(date_ele == null || (!!date_ele && date_ele.length == 0))
        return;

      date_ele.forEach(function (item) {
        date = _this[item.getAttribute('bind_name')];
        formart = item.getAttribute('format');

        if(!(date[1] instanceof Date) && date[0] instanceof Date){
          date.splice(1, 1, new Date());
          if(!!_this[item.getAttribute('bind_name') + '_items'])
            _this[item.getAttribute('bind_name') + '_items'][1] = {
              field: item.getAttribute('field'),
              value: formart == 'year' ? new Date().getFullYear() : new Date().toLocaleDateString(),
              operate: '<='
            };
        }
      })
    },
    reset_searchbox(area){
      let special = function (_this) {
        if (!!_this.authorlist)
          _this.authorlist.index = 1;
        if (!!_this.listRow)
          _this.listRow = {
            index: 1,
            max: _this.listRow.max
          };
        if (!!_this.listRow1)
          _this.listRow1 = {
            index: 1,
            max: _this.listRow1.max
          };
        if (!!_this.listRow2)
          _this.listRow2 = {
            index: 1,
            max: _this.listRow2.max
          };
        if (!!_this.listRow3)
          _this.listRow3 = {
            index: 1,
            max: _this.listRow3.max
          };
        if (!!_this.listSentence)
          _this.listSentence = 1;

        document.querySelectorAll('[bind_name]').forEach(function (item) {
          if(item.getAttribute('type') == 'item')
            _this[item.getAttribute('bind_name')] = '';
          if(item.getAttribute('type') == 'items' || item.getAttribute('type') == 'items2')
            _this[item.getAttribute('bind_name')] = [];
          if(item.getAttribute('type') == 'date')
            _this[item.getAttribute('bind_name')] = ['', ''];
        })
      }
      this.resetSrcBox(area, special);
    },
    sentence_reset(){
      this.s_public_time = ['', ''];
      this.s_updateTimeValItems = [];
      this.s_updateTimeVal = '';
    },
    major_reset(){
      this.m_public_time = ['', ''];
    },
  }
}
