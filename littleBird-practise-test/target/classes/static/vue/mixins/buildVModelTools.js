export default {
  data(){
    return{
      SCDB: '537,539,540,542,544,608,609,611,604,607,623,604,4722,4723,4724,4725,605,4726',
      sysId: 113
    }
  },
  methods:{
    getSearchComponents(callback, type, sourceType){
      let searchComponents = JSON.parse(sessionStorage.getItem('searchComponents') || '{}');
      if(searchComponents.hasOwnProperty(sourceType)){
        this.setSearchComponents(type, searchComponents[sourceType]);
        if(typeof callback == 'function'){
          callback(this);
        }
        return;
      }

      let _this = this;
      this.API.getMatchShow(this.query.sysId, this.tableId, '1').then(function (response) {
        if(response.status == 200 && !!response.data.dataList){
          _this.setSearchComponents(type, response.data.dataList);
          searchComponents[sourceType] = response.data.dataList;
          sessionStorage.setItem('searchComponents', JSON.stringify(searchComponents));
        }

        if(typeof callback == 'function'){
          callback(_this);
        }
      })
    },
    setSearchComponents(type, list){
      if(type){
        let arrTemp = [];
        for (let i = 0; i < list.length; i++) {
          if(list[i]['fieldPage'] == type)
            arrTemp.push(list[i]);
        }
        this['searchComponents'] = this.dataPartition(arrTemp);
      }else
        this['searchComponents'] = this.dataPartition(list);
    },
    dataPartition(data){
      let components = {}, type;
      for (let i = 0; i < data.length; i++) {
        if(components.hasOwnProperty(data[i]['fieldGroup'])){
          components[data[i]['fieldGroup']].push(data[i]);
        }else {
          components[data[i]['fieldGroup']] = [];
          components[data[i]['fieldGroup']].push(data[i]);
        }
      }

      let keys = Object.keys(components);
      for (let j = 0; j < keys.length; j++) {
        if(components[keys[j]] instanceof Array){
          type = components[keys[j]];
          for (let k = 0; k < components[keys[j]].length; k++) {
            if(type.hasOwnProperty(components[keys[j]][k]['fieldType'])){
              type[components[keys[j]][k]['fieldType']].push(components[keys[j]][k]);
            }else {
              type[components[keys[j]][k]['fieldType']] = [];
              type[components[keys[j]][k]['fieldType']].push(components[keys[j]][k]);
            }
          }
        }
      }
      return components;
    },
    searchExecute(queryModel, param) {
      this.loading_start('search_loading');

      let _this = this;
      this.API.dataSearchData(queryModel.toJson(), param).then(function (response){
        if(response.data.code == '200'){
          _this.listInfos = {
            'dataList': response.data.dataList,
            'total': response.data.total
          }
        }
        _this.itemsUpdate('total', response.total);
        _this.loading_over('search_loading');
      });
    },
    resultSearch(){
      if(document.querySelector('.search_area .titlw .c_titon').text != '句子检索')
        this.searchExecute(this.buildModel());
      else
        this.searchExecute(this.buildModel(), '?data_type=1&markRed=true&isLazy=true');
    },
    formatXMLData(para, num) {
      let parse = para && this.getSentence(para)[num].replace(/\$|\#/g, "");
      return parse.indexOf('Total="0"') > -1 ? '' : parse;
    },
    clsGroup_event(qModel, specialHandler){
      this.getKeys(qModel, specialHandler);
    },
    clone(obj){
      let keys = Object.keys(obj), clone = {};
      for (let key in keys)
        clone[keys[key]] = obj[keys[key]];
      return clone;
    },
    deepClone(obj) {
      let objClone = Array.isArray(obj) ? [] : {};
      if (obj && typeof obj === "object") {
        for (let key in obj) {
          if (obj.hasOwnProperty(key)) {
            if (obj[key] && typeof obj[key] === "object") {
              objClone[key] = this.deepClone(obj[key]);
            } else {
              objClone[key] = obj[key];
            }
          }
        }
      }
      return objClone;
    },
    sourceGroup_event(queryModel){
      let tableIds = queryModel.toJson().tableId, queryModels = [];
      switch (tableIds) {
        case '537,539,540,542,544' :
        case this.SCDB :
          let paper_config = {
            CLKJ: {
              tableId: '537'
            },
            CLKB: {
              tableId: '539'
            },
            CLKM: {
              tableId: '540'
            },
            CLKN: {
              tableId: '542'
            },
            CLKP: {
              tableId: '544'
            },
          }, CLKJ_model = queryModel, CLKB_model = queryModel, CLKM_model = queryModel, CLKP_model = queryModel, CLKN_model = queryModel;
          CLKJ_model.setTableId(paper_config["CLKJ"].tableId);
          CLKJ_model = CLKJ_model.toJson();

          CLKB_model.setTableId(paper_config["CLKB"].tableId);
          CLKB_model = CLKB_model.toJson();

          CLKM_model.setTableId(paper_config["CLKM"].tableId);
          CLKM_model = CLKM_model.toJson();

          CLKN_model.setTableId(paper_config["CLKN"].tableId);
          CLKN_model = CLKN_model.toJson();

          CLKP_model.setTableId(paper_config["CLKP"].tableId);
          CLKP_model = CLKP_model.toJson();

          queryModels = [{
            key: '期刊',
            query: CLKJ_model
          },{
            key: '博士',
            query: CLKB_model
          },{
            key: '硕士',
            query: CLKM_model
          },{
            key: '会议',
            query: CLKN_model
          },{
            key: '报纸',
            query: CLKP_model
          }];
          break;
        case '609,610,611,612' :
          let law_config = {
            CLKL_DL: {
              tableId: '609,610'
            },
            CLKL_XG: {
              tableId: '611,612'
            }
          }, CLKL_DL_model = queryModel, CLKL_XG_model = queryModel;
          CLKL_DL_model.setTableId(law_config["CLKL_DL"].tableId);
          CLKL_DL_model = CLKL_DL_model.toJson();

          CLKL_XG_model.setTableId(law_config["CLKL_XG"].tableId);
          CLKL_XG_model = CLKL_XG_model.toJson();

          queryModels = [{
            key: '中国大陆法规',
            query: CLKL_DL_model
          },{
            key: '中国香港法规',
            query: CLKL_XG_model
          }];
          break;
      }
      if(queryModels.length > 0)
        this.sourceGroupExecute(queryModels);
    },
    getBatchSearchTableId(queryModels, key){
      for (let i = 0; i < queryModels.length; i++) {
        if(key == queryModels[i].key)
          return queryModels[i].query.tableId;
      }
      return '';
    },
    sourceGroupExecute(queryModels){
      let _this = this;
      this.API.batchIndexSearchData(queryModels).then(function (response){
        if(!!response.data){
          let sourceGroupData = [], keys = Object.keys(response.data);
          for (let i = 0; i < keys.length; i++) {
            if(response.data[keys[i]].dataList.length > 0)
              sourceGroupData.push({
                source: keys[i],
                total: response.data[keys[i]].total,
                type: response.data[keys[i]].dataList[0].tablename,
                tableId: _this.getBatchSearchTableId(queryModels, keys[i])
              })
          }
          console.log(_this.selectionSort(sourceGroupData));
          _this.source_group_input_checked = [];
          _this.sourceGroup = sourceGroupData;
          if(!!_this.cls_open_num)
            _this.cls_open_states.splice(1, 1, _this.cls_open_num > 1);
        }
      });
    },
    selectionSort(arr){
      let index, temp, length = arr.length;

      for (let i = 0; i < length; i++){
        index = i;
        for (let j = i + 1; j < length; j++){
          if (arr[j]['total'] > arr[index]['total'])
            index = j;
        }
        if (index != i){
          temp = arr[i];
          arr[i] = arr[index];
          arr[index] = temp;
        }
      }
    },
    /**
     * groupKeys groupDataTemp暂时未使用
     * */
    getKeys(qModel, specialHandler){
      this.loading_start('group_loading');

      let _this = this;
      this.API.getMatchShow(this.query.sysId, this.query.tableId, '2').then(function (response){
        let list;
        if(response.data.code == '1' && !!response.data.dataList && response.data.dataList.length > 0 ){
          list = response.data.dataList;
          _this.groupKeys = [];
          _this.groupDataTemp = [];
          _this.groups = [];
          for (let i = 0; i < list.length; i++){
            _this.groupKeys.push(list[i]['fieldKey'])
            _this.groups.push({
              field: list[i]['fieldKey'],
              title: list[i]['displayname'],
              max: parseInt(list[i]['max']),
              top: parseInt(list[i]['top']),
              icon: 'static/clkd/images/' + list[i]['fieldKey'] + '.png',
              // icon: '../../../../static/clkd/images/' + list[i]['fieldKey'] + '.png',
              data: []})
          }
          if(_this.groupKeys.length == 0)
            _this.loading_over('group_loading');
          else
            _this.groupExecute(_this.groupKeys, 0, qModel, specialHandler);
        }
      });
    },
    /**
     * @param keys    需要分组的字段数组
     * @param flag    0
     */
    groupExecute(keys, flag, qModel, specialHandler){

      if(!(!!keys) || keys.length == 0)
        return;

      let queryModel = qModel || new this.$queryModel(), f = flag;
      queryModel.setSysId(this.query.sysId);
      queryModel.setTableId(this.query.tableId);
      queryModel.setStart(-1);
      queryModel.setLength(-1);
      queryModel.newSrchGroup(keys[flag], this.groups[flag].max, this.groups[flag].top);

      let model;
      if(typeof specialHandler == 'function') {
        model = specialHandler(queryModel, keys[flag])
      }else
        model = queryModel.toJson();

      let _this = this;
      this.API.groupSearchGroup(model).then(function (response){
        if(!!response.data.dataList && !!response.data.dataList.length > 0){
          _this.groupDataTemp[keys[f]] = response.data.dataList;
          //
          _this.groups[flag]['data'] = response.data.dataList;
        }
        /* 执行结束 将缓存添加至groupData */
        if(flag == keys.length - 1){
          _this.groupData = _this.groupDataTemp;
          //重置展开分组
          if(!!_this.cls_open_num){
            for (let i = 0; i < Object.keys(_this.groupData).length; i++) {
              _this.cls_open_states.splice(i + 2, 1, _this.cls_open_num > i + 2)
            }
            _this.cls_open_states.splice(0, 1, _this.cls_open_num > 0);
          }

          _this.loading_over('group_loading');
        }
        /* 依次对每个字段进行分组 */
        keys.length > flag + 1 && _this.groupExecute(keys, ++flag, qModel, specialHandler);
      });
    },
    /** 条件清空 */
    empty(type){
      //清空虚拟条件模型
      this.resetItems();
      document.querySelectorAll('.' + type + '-cond-item').forEach(function (item) {
        item.value = '';
      })
    },
    /**
     * queryModel构建 type(目前已使用的分类)：senior/sentence/major/result/public
     */
    joinConditionItems(type, conditionItems, extend_handler){
      let items = !!conditionItems ? conditionItems : {
        'logics': 'AND',
        'items': []
      }, _this = this, date = [];
      document.querySelectorAll('.' + type + '-cond-item').forEach(function (item) {
        let e = item,
          /* 当前type为items时 意为多条件项 此时 value绑定为选中项数组 例：[{'field': '', 'name': '', 'value': '' ..}, ...] */
          t = e.getAttribute('type'),
          b = e.getAttribute('bind_name'),
          f = e.getAttribute('field'),
          v = !!_this[b] ? _this[b] : (!!e.value ? e.value : e.getAttribute('value')),
          l = e.getAttribute('logic') || 'AND',
          o = e.getAttribute('operate') || '',
          s = e.getAttribute('logics') || '';
        if(t == 'item' && !!v && !!v.trim()){
          items['items'].push({'field': f, 'value': v, 'logic': l || 'AND', 'operate': o || '', 'logics': s});
        }else if(t == 'items'){
          if(_this[b] instanceof Array && _this[b].length > 0){
            if(typeof _this[b]['logics'] == 'undefined')
              _this[b]['logics'] = s || 'AND';
            items['items'].push(_this[b]);
          }
        }else if(t == 'items2'){
          if(!!_this[b][parseInt(e.getAttribute('index'))])
            items['items'] = items['items'].concat([_this[b][parseInt(e.getAttribute('index'))]]);
          /* 当条件为日期范围时 type设置为date 非日期范围item即可 */
        }else if(t == 'date'){
          // if(_this[b] instanceof Array && _this[b].length == 2 && !!_this[b][0] && !!_this[b][1]){
          if(!!_this[b] && _this[b] instanceof Array){
            date = [
              !!_this[b][0] && _this[b][0] instanceof Date ? _this[b][0].toLocaleDateString() : '',
              !!_this[b][1] && _this[b][1] instanceof Date ? _this[b][1].toLocaleDateString() : ''
            ];
            date['field'] = f;
            date['logic'] = l;
            date['operate'] = o;
          }
        }
      });

      this.$emit('itemsUpdate', ['conds.' + type + 'Items', 'date'], [items, date]);

      if(!!extend_handler && typeof extend_handler == 'function')
        extend_handler(_this);
    },
    /* 根据虚拟query模型构建真实queryModel */
    buildModel(){
      return this.setQueryModel();
    },
    /*
    * 根据虚拟query模型构建queryModel
    * */
    setQueryModel(){
      let queryModel = this.qModel || new this.$queryModel();
      /* 检索范围 */
      let q = queryModel.toJson();
      if(q.tableId == 0)
        queryModel.setTableId(this.query.tableId);
      if(q.sysId == 0)
        queryModel.setSysId(this.query.sysId);
      /* 条件项 */

      // join special sentence conditons
      let sItem, sentence_condItems;
      if(!!this.query.sentenceConds && this.query.sentenceConds.length > 0){
        queryModel.newSrchLogic(this.query.sentenceConds['logics'] || 'AND');
        for (let i = 0; i < this.query.sentenceConds.length; i++) {
          sItem = this.query.sentenceConds[i];
          if(typeof sItem == 'undefined'){
            continue;
          }
          if(!!sItem.value1 && !!sItem.value2)
          queryModel.newSrchItem(sItem.field, (sItem.value1 || '') + ' ' + sItem.type + ' ' + (sItem.value2 || ''), sItem.logic, sItem.operate);
        }
      }

      // join common conditons
      let keys = Object.keys(this.query.conds);
      for (let key of keys)
        this.build_conditions(queryModel, this.query.conds[key].items)

      /* 排序项 */
      let orders = this.query.order;
      switch (orders.length) {
        case 1 :
          queryModel.setOrder(orders[0]['field'], orders[0]['sort']);
          break;
        case 2 :
          queryModel.setOrder(orders[0]['field'], orders[0]['sort'], orders[1]['field'], orders[1]['sort']);
          break;
      }

      /* date */
      if(!!this.query.date && this.query.date.length == 2)
        queryModel.setBetween(this.query.date['field'], this.query.date[0], this.query.date[1], this.query.date['logic'] || 'AND');

      /* page */
      queryModel.setStart((this.query.pageNum - 1) * this.query.pageSize);
      queryModel.setLength(this.query.pageSize);

      /* 额外需要执行的回调 如要对queryModel进行扩展 可传入回调函数在该函数中对queryModel进行操作 */
      if(typeof this.query.callbacks == 'function')
        this.query.callbacks(queryModel);
      if(typeof this.query.callbacks == 'array')
        for (let i = 0; i < this.query.callbacks.length; i++) {
          if(typeof this.query.callbacks[i] == 'function')
            this.query.callbacks[i](queryModel);
        }
      return queryModel;
    },
    build_conditions(queryModel, arr){
      if(arr instanceof Array && arr.length > 0)
        arr = this.orderConditions(arr);

      if(arr.length > 0 && !(arr[0] instanceof Array))
        queryModel.newSrchLogic(arr.logics || 'AND');
      for (let i = 0; i < arr.length; i++) {
        if(arr[i] instanceof Array && arr[i].length > 0){
          this.build_conditions(queryModel, arr[i]);
        }
        else if(!(arr[i] instanceof Array)){
          if(!!arr[i]['field'] && arr[i]['value'])
            queryModel.newSrchItem(arr[i]['field'], arr[i]['value'], arr[i]['logic'] || 'AND', arr[i]['operate'] || '');
        }
      }
    },
    orderConditions(array){
      if(!(array instanceof Array))
        return [];
      let orderArr = [];
      for (let j = 0; j <array.length; j++) {
        if(!(array[j] instanceof Array) && !!array[j].field && !!array[j].value)
          orderArr.push(array[j]);
      }
      for (let i = 0; i < array.length; i++) {
        if(array[i] instanceof Array)
          orderArr.push(array[i]);
      }
      return orderArr;
    },
    //keys与values个数需要一致
    itemsChange(keys, values, isSearch){
      this.itemsUpdate(keys, values);
      isSearch && this.resultSearch();
    },
    // 最多支持二级  如 query: { conds: {seniorItems: [], ...}, tableid: ...}  最多支持到conds.seniorItems
    // 一般应该用不到
    itemsRemove(keys){
      if(typeof keys == 'string')
        keys = [keys];
      let key;
      for (let i = 0; i < keys.length; i++) {
        key = keys[i];
        if(key.indexOf('.') > -1){
          key = key.split('.');
          delete this.query[key[0]][key[1]];
        }
        else
          delete this.query[key];
      }
    },
    /**
     * @param keys     (key   AND value)        OR
     * @param value    (keys  AND values)
     */
    itemsUpdate(keys, value) {
      let key, query;
      if(typeof keys == 'string')
        /* 合并分支 */
        keys = [keys], value = [value];
      query = this.newQuery();
      for (let i in keys){
        key = keys[i];
        if(key == 'tableId' && value[i] == ''){
          query.tableId = this.tableId0;
          continue;
        }
        if(key.indexOf('conds') > -1 && key.indexOf('.') > -1){
          let spl = key.split('.'), conditionKey = spl[1];
          if(JSON.stringify(this.query[spl[0]][conditionKey]) == JSON.stringify(value[i]))
            continue;
          query[spl[0]][conditionKey] = value[i];
        }else{
          if(JSON.stringify(this.query[key]) == JSON.stringify(value[i]))
            continue;
          query[key] = value[i];
        }
      }
      this.query = query;
    },
    /* 重置虚拟query为初始状态 */
    resetItems(){

    },
    newQuery(){
      let keys = Object.keys(this.query), query = {};
      for (let key in keys)
        query[keys[key]] = this.query[keys[key]];
      return query;
    },
    /** 恢复地址栏条件
     * 实现思路（每一项都要双向绑定）  区分检索类型  获取对应类型-cond-items  存入 {‘bind_name': '', 'value': ''}样的数组
     * 1.传递虚拟query(转换为JSON对象)至结果页
     * 2.获取相关条件项 如conds各项、date等
     * 如果是tree value传选中项数组 可创建隐藏域辅助
     *  */
    conditionForward(type){
      let conditions = [];
      document.querySelectorAll('.' + type + '-conds-item').forEach(function (item, index) {
        conditions.push({'type': item.getAttribute('type'), 'bind_name': item.getAttribute('bind_name'), 'value': item.value || item.getAttribute('value')});
      })
      localStorage.setItem('urlForwardConditions', JSON.stringify(conditions));
    },
    /**
     * type
     *   - item: 直接this[bind_name] = value;
     *   - items:获取页面bind_name=bind_name的ele,ele[i] = this[bind_name][i];
     * */
    restoreConds(conditions){
      let urlForwardConditions = JSON.parse(localStorage.getItem('urlForwardConditions'));
      for (let i = 0; i < urlForwardConditions.length; i++) {
        if(urlForwardConditions[i]['type'] == 'item')
          this[urlForwardConditions[i]['bind_name']] = urlForwardConditions[i]['value'];
        else if(urlForwardConditions[i]['type'] == 'items'){

        }
      }
    },
    /**
     * 条件解释
     * s_label 动态绑定为展示标签
     * <input field="case_no" type="item" bind_name='vue绑定变量名字' s_label='合同纠纷' logic='AND' class="cond-item">
     *  */
    condExplain(){},
    /**
     -* ------------------------------------------------ page conditions element real time synchronization -------------------------------------------------
     * 构建页面虚拟条件模型 根据需要自定义该函数 最终结果需要将[{'field': '', 'value': ...}]更新至虚拟query中如this.$emit('itemsUpdate', 'conds.demoItems', [...])
     * 句子检索专属的特殊类型 通过绑定change事件和joinSentence方法 添加至query.sentence中  句子检索元素上可以不填写type来避免此处的重复追加
     * */
    joinSentenceItems(){
      this.$emit('itemsUpdate', ['conds', 'tableId', 'pageNum'], [{}, '', 1]);
      //添加query.conds下句子条件(句子检索功能下若有普通的检索条件并入到该项下)
      this.joinConditionItems('public');
      this.joinConditionItems('sentence');
      //添加query.sentenceConds条件
      this.$emit('itemsUpdate', ['sentenceConds'], [this['sentenceConds']]);
    },
    //type = item
    condItemChange(e){
      let condition = e.target.parentNode.parentNode,
        f = this.getValueByType(condition, 'field'),
        l = this.getValueByType(condition, 'logic'),
        o = this.getValueByType(condition, 'match'),
        vItem = condition.querySelector('[urtp-data-value]');
      vItem.setAttribute('field', f || '');
      vItem.setAttribute('logic', l);
      vItem.setAttribute('operate', o);
    },
    //type = items and with bind_name
    condItemsChange(e, key, searchComponentName){
      this.condItemChange(e);

      if(!!document.querySelector('input[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').value &&
      !!document.querySelector('input[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').value.trim())
        this[key + '_items'] = [{
          field: this.searchComponents[searchComponentName][0]['fieldKey'],
          value: document.querySelector('input[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').value.replaceAll('+', ' + ').trim(),
          operate: document.querySelector('input[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').getAttribute('operate'),
          logic: 'or'
        }]
      else
        this[key + '_items'] = [{}];
    },
    condGroupChange(event, index, condItemsName){
      let condition = event.target.parentNode.parentNode;
      if(!!this.getValueByType(condition, 'value') && !!this.getValueByType(condition, 'value').trim())
        this[condItemsName][index] = {
          'field': this.getValueByType(condition, 'field') || '',
          'value': this.getValueByType(condition, 'value').trim(),
          'operate': this.getValueByType(condition, 'match'),
          'logic': this.getValueByType(condition, 'logic')
        }
      else
        this[condItemsName][index] = {};
    },
    /**
     * logics 组逻辑连接符
     * logic  条件1逻辑连接符
     * logic2 条件2逻辑连接符
     * parentLevel条件组的单条件的父级位于urtp-data-*的上几级 默认2
     * 适用于
     *  + -     field value1 logic value2 match
     *  logics  field value1 logic value2 match
     */
    condGroupChange2(event, index, condItemsName, parentLevel){
      let condition, tmp = event.target;
      if(parentLevel) {
        for (let i = 0; i < parentLevel; i++)
          tmp = tmp.parentNode;
        condition = tmp;
      } else
        condition = event.target.parentNode.parentNode;
      if(!!this.getValueByType(condition, 'value') && !!this.getValueByType(condition, 'value').trim())
        this[condItemsName][index][0] = {
          'field': this.getValueByType(condition, 'field') || '',
          'value': this.getValueByType(condition, 'value').trim(),
          'operate': this.getValueByType(condition, 'match') || "=",
          'logic': 'AND'
        }
      else
        this[condItemsName][index][0] = {};
      if(!!this.getValueByType(condition, 'value2') && !!this.getValueByType(condition, 'value2').trim())
        this[condItemsName][index][1] = {
          'logic': this.getValueByType(condition, 'logic2') || 'AND',
          'field': this.getValueByType(condition, 'field2') || this.getValueByType(condition, 'field') || '',
          'value': this.getValueByType(condition, 'value2').trim(),
          'operate': this.getValueByType(condition, 'match2') || this.getValueByType(condition, 'match')
        }
      else
        this[condItemsName][index][1] = {};

      if((this.getValueByType(condition, 'value') == null || (!!this.getValueByType(condition, 'value') && this.getValueByType(condition, 'value').trim() == ''))
        && (this.getValueByType(condition, 'value2') == null || (!!this.getValueByType(condition, 'value2') && this.getValueByType(condition, 'value2').trim() == '')))
        this[condItemsName][index] = [];
      this[condItemsName][index]['logics'] = this.getValueByType(condition, 'logics') || 'AND';
    },
    condSentenceChange(event, index, condItemsName){
      let condition = event.target.parentNode.parentNode;
      if(!!this.getValueByType(condition, 'value1') && !!this.getValueByType(condition, 'value1').trim() &&
      !!this.getValueByType(condition, 'value2') && !!this.getValueByType(condition, 'value2').trim())
        this[condItemsName][index] =
          {
            'field': this.getValueByType(condition, 'field') || '',
            'value1': this.getValueByType(condition, 'value1'),
            'value2': this.getValueByType(condition, 'value2'),
            'type': this.getValueByType(condition, 'type'),
            'logic': this.getValueByType(condition, 'logic'),
            'operate': this.getValueByType(condition, 'match'),
          }
      else
        this[condItemsName][index] = {};
    },
    getValueByType(condition, type){
      return condition.querySelector('[urtp-data-' + type + ']') == null
        ? '' : (condition.querySelector('[urtp-data-' + type + ']').value || condition.querySelector('[urtp-data-' + type + ']').getAttribute('value'))
    },
    popCondsChange(key, searchComponentName){
      if(!!document.querySelector('[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').value && !!document.querySelector('[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').value.trim())
        this[key + '_items'] = [{
          field: this.searchComponents[searchComponentName][0]['fieldKey'],
          value: document.querySelector('[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').value.replaceAll('+', ' + '),
          operate: document.querySelector('[field="' + this.searchComponents[searchComponentName][0]['fieldKey'] +  '"]').getAttribute('operate'),
          logic: 'or'
        }]
      else
        this[key + '_items'] = [{}];
    },
    yearRangeChange(field, key, type){
      if(!!this[key][0] && !!this[key][1] && this[key][0] > this[key][1]){
        let t = this[key][0];
        this[key].splice(0, 1, new Date(this[key][1].toLocaleDateString()));
        this[key].splice(1, 1, new Date(t.toLocaleDateString()));
      }

      this.$nextTick(function () {
        if(!(this[key][0] instanceof Date) && !(this[key][1] instanceof Date))
          this[key + '_items'] = ['', ''];

        if(this[key][0] instanceof Date)
          this[key + '_items'][0] = {
            field: field,
            value: type == 'date' ? this[key][0].toLocaleDateString() : this[key][0].getFullYear(),
            operate: '>='
          };
        if(this[key][1] instanceof Date)
          this[key + '_items'][1] = {
            field: field,
            value: type == 'date' ? new Date().toLocaleDateString() : new Date().getFullYear(),
            operate: '<='
          };
      })
    },
    /**
     * 获取下拉封装对象 { options: [{ name: '', value: '' }] }
     */
    getOptions(key, grade){
      let data = {
        options: []
      };
      if(grade){
        for (let i = 0; i < this.searchComponents[key]['droplist'].length; i++) {
          data['options'].push({
            name: this.searchComponents[key]['droplist'][i]['displayname'],
            value: this.searchComponents[key]['droplist'][i]['fieldKey']
          })
        }
      }else{
        for (let i = 0; i < this.searchComponents[key].length; i++) {
          data['options'].push({
            name: this.searchComponents[key][i]['displayname'],
            value: this.searchComponents[key][i]['fieldKey']
          })
        }
      }
      return data;
    },
    loading_start(key){
      if(typeof this[key] != 'undefined')
        this[key] = true;
    },
    loading_over(key){
      if(typeof this[key] != 'undefined')
        this[key] = false;
    },
    /**
     * ------------------------------------------------- page conditions element real time synchronization --------------------------------------------------
     * */
  }
}
