import {encrypt} from "urtp-aes-encrypt";

export default {
  data() {
    return {
      _time_out: null
    }
  },
  methods: {
    /**
     * 功能描述: 划词，区域绑定keyup事件即可　
     * @author: slf
     * @date: 2022/7/2 16:30
     */
    delimitation(event) {
      let range = window.getSelection().getRangeAt(0), rangeType = window.getSelection().type;
      if (rangeType != 'Range')//未选择有效区间
        return '';
      if (range.startContainer.parentNode != range.endContainer.parentNode)//禁止跨p节点
        return '';
      /* 注：此方式仅能正确获取区域字符串,偏移量切勿取此处！ */
      let ele = event.target,
        selTxt = (window.getSelection ? window.getSelection() : document.selection.createRange().text).toString();
      let startOffset = range.startOffset, endOffset = range.endOffset;
      let childNodes = ele.childNodes;
      /* 单标签下首次标红处理 */
      if (childNodes.length == 1) {
        let nodeText = childNodes[0].textContent.trim();
        let prefix = nodeText.substring(0, startOffset);
        let middle = '<span style="color: ' + this.color + ';">' + nodeText.substring(startOffset, endOffset) + '</span>';
        let suffix = nodeText.substring(endOffset, nodeText.length);
        ele.innerHTML = prefix + middle + suffix;
      }
      if (childNodes.length > 1) {
        /* 所选区域未跨节点 */
        if (range.startContainer == range.endContainer) {
          for (let index1 = 0; index1 < childNodes.length; index1++) {
            if (childNodes[index1] == range.startContainer ||
              childNodes[index1] == range.startContainer.parentNode) {
              let nodeText = childNodes[index1].textContent;
              let prefix = nodeText.substring(0, startOffset);
              let middle = '<span style="color: ' + this.color + ';">' + nodeText.substring(startOffset, endOffset) + "</span>";
              let suffix = nodeText.substring(endOffset, nodeText.length);
              // $(childNodes[index]).replaceWith( prefix + middle + suffix);
              this.replaceWithElement(ele, prefix + middle + suffix, index1);
            }
          }
        } else {
          let isStart = false;
          let repalce_span = ""
          for (let index2 = 0; index2 < childNodes.length; index2++) {
            if (childNodes[index2] == range.startContainer || childNodes[index2] == range.startContainer.parentNode) {
              isStart = true;
              let nodeText = childNodes[index2].textContent, type = childNodes[index2].nodeName.toLowerCase();
              let prefix;
              prefix = type == 'span' ? '<span style="color: ' + this.color + ';">' + nodeText.substring(0, startOffset) + '</span>' : nodeText.substring(0, startOffset);
              let suffix = '<span style="color: ' + this.color + ';">' + nodeText.substring(startOffset, nodeText.length);
              repalce_span = prefix + suffix;
              childNodes[index2].replaceWith('');
            } else if (childNodes[index2] == range.endContainer ||
              childNodes[index2] == range.endContainer.parentNode) {
              isStart = false;
              let nodeText = childNodes[index2].textContent, type = childNodes[index2].nodeName.toLowerCase();
              let prefix = nodeText.substring(0, endOffset) + "</span>";
              let suffix;
              suffix = type == 'span' ? '<span style="color: ' + this.color + ';">' + nodeText.substring(endOffset, nodeText.length) + "</span>" : nodeText.substring(endOffset, nodeText.length);
              repalce_span += prefix + suffix;
              this.replaceWithElement(ele, repalce_span, index2);
              // $(childNodes[index]).replaceWith(repalce_span);
              break;
            } else {
              if (isStart) {
                repalce_span += childNodes[index2].textContent;
                childNodes[index2].replaceWith('');
              }
            }
          }
        }
      }
      return selTxt;
    },
    /**
     * 功能描述:　
     * @param selector 类选择器字符串
     * @param special_handler
     * @author: slf
     * @date: 2022/7/2 16:31
     */
    resetSrcBox(selector, special_handler) {
      //清空所有元素的条件，并且需要将新增的检索项删除，恢复原有的样子
      if (typeof special_handler == 'function')
        special_handler(this);

      this.$nextTick(function () {
        //如果支持过滤的树 则清除输入内容
        let filters = document.querySelectorAll('[role="tooltip"]'), input = '';
        if (!!filters) {
          for (let m = 0; m < filters.length; m++) {
            input = filters[m].querySelectorAll('input');
            if (!!input && input.length > 0)
              input[0].value = '';
          }
        }

        //取指定元素下的所有文本框、下拉框、日历框，并且设置对应的值为空，注意这里要判断对应的目标dom的标签类型
        const resetArea = document.getElementsByClassName(selector);

        //遍历所有的子节点
        function traverseChildNodes(parentNode) {
          if (!!parentNode && !!parentNode.childNodes && parentNode.childNodes.length > 0) {
            let childNodes = parentNode.childNodes;
            for (let k in childNodes) {
              let child = childNodes[k];
              let tagName = child.tagName;
              // console.log(tagName);
              if (tagName == 'SELECT') {
                child.value = child.options[0].value;
              }
              if ((tagName == 'INPUT' || tagName == 'TEXTAREA') && (child.type != 'button' && child.type != 'submit') && child.className.indexOf('ignore') == -1) {
                child.value = ''
                child.setAttribute('value', '');
              }
              traverseChildNodes((child));
            }
          }
        }

        for (let i = 0; i < resetArea.length; i++) {
          traverseChildNodes(resetArea[i])
        }
      })
    },
    /**
     * 功能描述: 回到页面指定height处
     * @param height
     * @author: slf
     * @date: 2022/7/2 16:32
     */
    goTop(height) {
      let timer = setInterval(function () {
        let left = window.pageYOffset;
        let step = Math.ceil((left - 0) / 10);
        window.scroll(0, left - step);
        if (left <= (height || 0)) {
          clearInterval(timer);
        }
      }, 20)
    },
    /**
     * 功能描述: 高频率执行事件优化
     * @param delay_times   触发事件后间隔时间内无新触发事件则执行事件,否则新事件取代旧事件执行(单位ms)
     * @param callback      事件执行动作
     * @author: slf
     * @date: 2022/6/28 17:11
     */
    reduceFrequency(delay_times, callback) {
      let timer_settings = {
        delay_time: delay_times,
        callback: callback
      }
      if (this._time_out != null) {
        clearTimeout(this._time_out);
        this._time_out = null;
        this._time_out = setTimeout(function () {
          timer_settings.callback();
          this._time_out = null;
        }, timer_settings.delay_time);
      } else {
        this._time_out = setTimeout(function () {
          timer_settings.callback();
          this._time_out = null;
        }, timer_settings.delay_time);
      }
    },
    dataURLtoFile(dataurl, filename) {
      let arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, {type: mime});
    },
    fileToBlob(file, callback) {
      const type = file.type;
      const reader = new FileReader();
      reader.readAsArrayBuffer(file);
      reader.onload = function (evt) {
        const blob = new Blob([evt.target.result], {type});
        if (typeof callback === 'function') {
          callback(blob)
        } else {
          console.log("我是 blob:", blob);
        }
      };
      // reader.readAsDataURL(file);
    },
    blobToBase64(blob, callback) {
      let r = new FileReader();
      r.onload = function (e) {
        if (typeof callback === 'function') {
          callback(e.target.result);
        } else {
          console.log("我是 base64: ", e.target.result);
        }
      }
      r.readAsDataURL(blob);
    },
    fileToBase64(file, callback) {
      const reader = new FileReader();
      reader.onload = function (evt) {
        if (typeof callback === 'function') {
          callback(evt.target.result)
        } else {
          console.log("我是base64:", evt.target.result);
        }
      };
      reader.readAsDataURL(file);
    },
    /**
     * 替换jQuery_replaceWith
     * @param element  替换父元素
     * @param newCon   替换内容
     * @param childIndex  是否指定仅替换某个指定孩子节点,number,默认替换全部
     */
    replaceWithElement(element, newCon, childIndex) {
      if (typeof childIndex != 'number')
        childIndex = false;
      let childNodes = element.childNodes, context = '';
      for (let i = 0; i < childNodes.length; i++) {
        if (childIndex == i) {
          context += newCon;
          continue;
        }
        context += childNodes[i].outerHTML || childNodes[i].textContent;
      }
      element.innerHTML = (typeof childIndex == 'number' ? context : newCon);
    },
    CNDate(strDate, isIgnoreZero) {
      if (!strDate) return '';
      let elist = ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
      let t, cn_date = '';
      for (let i = 0; i < strDate.length; i++) {
        t = strDate.substr(i, 1);
        switch (cn_date.length) {
          case 4 :
            cn_date += '年';
            break;
          case 7 :
            cn_date += '月';
            break;
        }
        if (t >= '0' && t <= '9')
          cn_date += elist[parseInt(t)];
        else
          continue;
      }
      if (isIgnoreZero) {
        let temp = cn_date.split('年');
        for (let i = 0; i < elist.length - 1; i++) {
          if (temp[1].indexOf('〇' + elist[i + 1]) > -1)
            temp[1] = temp[1].replaceAll('〇' + elist[i + 1], elist[i + 1]);
        }
        cn_date = temp[0] + '年' + temp[1];
      }
      return cn_date;
    },
    blobDownload(name, blob) {
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        //兼容ie浏览器
        window.navigator.msSaveOrOpenBlob(blob, name)
      } else {
        //谷歌,火狐等浏览器
        let url = window.URL.createObjectURL(blob);
        let downloadElement = document.createElement("a");
        downloadElement.style.display = "none";
        downloadElement.href = url;
        downloadElement.download = name;
        document.body.appendChild(downloadElement);
        downloadElement.click();
        document.body.removeChild(downloadElement);
        window.URL.revokeObjectURL(url);
      }
    },
    getKeys() {
      let _this = this;
      this.API.dataSearch('/api/productdata/get_match_show/' + this.query.sysId + '/' + this.query.tableId + '/2').then(function (response) {
        let list;
        if (response.data.code == '1' && !!response.data.dataList && response.data.dataList.length > 0) {
          list = response.data.dataList;
          _this.groupKeys = [];
          _this.groupDataTemp = [];
          for (let i = 0; i < list.length; i++)
            _this.groupKeys.push(list[i]['fieldKey'])
          _this.group(_this.groupKeys, 0);
        }
      });
    },
    /**
     * @param keys    需要分组的字段数组
     * @param flag    0
     */
    group(keys, flag) {

      if (!(!!keys) || keys.length == 0)
        return;

      let queryModel = this.setQueryModel(), f = flag;
      queryModel.setStart(-1);
      queryModel.setLength(-1);
      queryModel.newSrchGroup(keys[flag], -1, 0);

      let _this = this;
      this.API.dataSearch('/api/dataSearch/getGroupKData?data_type=1', queryModel.toJson()).then(function (response) {
        if (!!response.data.dataList && !!response.data.dataList.length > 0)
          _this.groupDataTemp[keys[f]] = response.data.dataList;
        /* 执行结束 将缓存添加至groupData */
        if (flag == keys.length - 1)
          _this.groupData = _this.groupDataTemp;
        /* 依次对每个字段进行分组 */
        keys.length > flag + 1 && _this.group(keys, ++flag);
      });
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
    bindEnterEvent() {
      $(document).unbind('keyup').on('keyup', '.senior_search_components input', this.enterSearch);
    },
    enterSearch(event) {
      let code = event.keyCode || event.which;
      if (code == 13)
        document.querySelector('#btnSearch').click();

      event.cancelBubble = true;
    },
    commonJump(path, params) {
      window.open(this.$router.resolve({
        path: path,
        query: params
      }).href, '_blank');
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
    changeValidate(key) {
      if (!!this[key][0] && !!this[key][1] && this[key][0] > this[key][1]) {
        let t = this[key][0];
        this[key].splice(0, 1, new Date(this[key][1].toLocaleDateString()));
        this[key].splice(1, 1, new Date(t.toLocaleDateString()));
      }
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
  }
}
