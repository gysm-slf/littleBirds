export default {
  data() {
    return {
      type: '',
      values: [],
      default_validate_item: ['empty', 'greater120', 'specialCode', 'dateValidate']
    }
  },
  methods: {
    validate(type, validate_items) {
      let res;

      this.type = type;
      this.getValues();
      validate_items = !!validate_items ? validate_items : this.default_validate_item;

      for (let i = 0; i < validate_items.length; i++) {
        if(typeof this[validate_items[i]] == 'function'){
          res = this[validate_items[i]]();
          if(!res.status)
            break;
        }
      }
      return res;
    },
    empty () {
      let res = {
        status: true,
        msg: ''
      };
      if(this.values.length == 0){
        res.status = false;
        res.msg = '未输入任何检索信息';
      }
      return res;
    },
    greater120 () {
      let res = {
        status: true,
        msg: ''
      };
      for (let i = 0; i < this.values.length; i++) {
        if (!$urtp.isNull(this.values[i]) && this.values[i].length > 120) {
          res.status = false;
          res.msg = '检索词最大长度为120个字符';
        }
      }
      return res;
    },
    specialCode() {
      let res = {
        status: true,
        msg: ''
      };
      for (let i = 0; i < this.values.length; i++) {
        if (!$urtp.isNull(this.values[i])) {
          if ($urtp.specialCode(this.values[i])) {
            res.status = false;
            res.msg = '输入条件不符合规范';
          }
        }
      }
      return res;
    },
    /*
    * 示例： type="senior" 会校验class="senior_search_components"下所有日期
    * */
    dateValidate(){
      let res = {
        status: true,
        msg: ''
      }, date_ele = document.querySelectorAll('.' + this.type + '_search_components [type="date"]'), date = ['', ''], dateTmp = [], _this = this;
      if(date_ele == null || (!!date_ele && date_ele.length == 0))
        return res;

      for (let i = 0; i < date_ele.length; i++) {
        dateTmp = date_ele[i].querySelectorAll('input');
        if(dateTmp.length == 0 && date_ele[i].nextElementSibling.getAttribute('type') == 'items'){
          dateTmp = date_ele[i].nextElementSibling.querySelectorAll('input');
        }
        date = [dateTmp[0].value, dateTmp[1].value];
        if(!date[0] && !!date[1]){
          res.status = false;
          res.msg = '起始日期不能为空';
        }
      }
      return res;
    },
    /*dateValidate(){
      let res = {
        status: true,
        msg: ''
      }, date_ele = document.querySelectorAll('.' + this.type + '_search_components [type="date"]'), date = ['', ''], _this = this;
      if(date_ele == null || (!!date_ele && date_ele.length == 0))
        return res;

      date_ele.forEach(function (item) {
        date = _this[item.getAttribute('bind_name')]

        if(!(date[0] instanceof Date) && date[1] instanceof Date){
          res.status = false;
          res.msg = '起始日期不能为空';
        }else if(!(date[1] instanceof Date) && date[0] instanceof Date)
          date.splice(1, 1, new Date());
      })
      return res;
    },*/
    clsValidate(){
      let res = {
        status: true,
        msg: ''
      };
      let type = document.querySelector('.sidebar .bor_top .cls_cur1 a').text;
      let chosed1 = false, chosed2 = false, chosed3 = false;
      switch (type) {
        case '案由导航' :
          chosed1 = true;
          break;
        case '发布机关' :
          chosed2 = true;
          break;
        case '知识导航' :
          chosed3 = true;
          break;
      }
      if((chosed1 && !this.hasSelected('reasonTree')) ||
        (chosed2 && !this.hasSelected('officeTree')) ||
        (chosed3 && !this.hasSelected('knowledgeTree'))){
        res.status = false;
        res.msg = '请您至少选择一个导航条件';
      }
      return res;
    },
    majorValidate () {
      let val = this.query.conds.majorItems.items.length > 0 ? this.query.conds.majorItems.items[0].value : '';
      let c = /[><=%]/ig, res = {
        status: true,
        msg: ''
      };
      if (!c.test(val)) {
        res.status = false;
        res.msg = '请输入正确的检索表达式';
        return res;
      }
      let e = /\'\s(or|and)\s\'/i;
      if (e.test(val)) {
        res.status = false;
        res.msg = '请输入正确的检索表达式';
      }
      return res
    },
    getValues(){
      let keys = Object.keys(this.query.conds), condItems;
      this.values = [];
      if(this.type == 'sentence'){
        condItems = this.query.sentenceConds;
        for (let l = 0; l < condItems.length; l++) {
          if(!!condItems[l].value1 && condItems[l].ignore != true)
            this.values.push(condItems[l].value1);
          if(!!condItems[l].value2 && condItems[l].ignore != true)
            this.values.push(condItems[l].value2);
        }
      }else{
        keys = Object.keys(this.query.conds);
        for (let i = 0; i < keys.length; i++) {
          condItems = this.query.conds[keys[i]].items;
          for (let j = 0; j < condItems.length; j++) {
            if(condItems[j] instanceof Array){
              for (let k = 0; k < condItems[j].length; k++) {
                if(!!condItems[j][k] && !!condItems[j][k].value && condItems[j][k].ignore != true)
                  this.values.push(condItems[j][k].value);
              }
            }else{
              if(!!condItems[j] && !!condItems[j].value && condItems[j].ignore != true)
                this.values.push(condItems[j].value);
            }
          }
        }
      }

      for (let i = 0; i < this.query.date.length; i++) {
        if(!!this.query.date[i])
          this.values.push(this.query.date[i]);
      }

    },
  }
}
