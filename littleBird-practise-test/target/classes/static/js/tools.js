/**
 * 转换类
 */
export function dataURLtoFile(dataurl, filename) {//将base64转换为文件
  let arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
    bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new File([u8arr], filename, {type: mime});
}
// 文件类型转Blob
export function fileToBlob(file, callback){
  const type = file.type;
  const reader = new FileReader();
  reader.readAsArrayBuffer(file);
  reader.onload = function(evt) {
    const blob = new Blob([evt.target.result], {type});
    if(typeof callback === 'function') {
      callback(blob)
    } else {
      console.log("我是 blob:", blob);
    }
  };
  // reader.readAsDataURL(file);
}
// Blob 转 Base64
export function blobToBase64(blob, callback){
  let r = new FileReader();
  r.onload = function (e) {
    if (typeof callback === 'function') {
      callback(e.target.result);
    } else {
      console.log("我是 base64: ", e.target.result);
    }
  }
  r.readAsDataURL(blob);
}
export function fileToBase64(file, callback){
  const reader = new FileReader();
  reader.onload = function(evt) {
    if(typeof callback === 'function') {
      callback(evt.target.result)
    } else {
      console.log("我是base64:", evt.target.result);
    }
  };
  reader.readAsDataURL(file);
}

/**
 * 划词标红,绑定前需要清除节点内空格等无效数据
 *    如: innerHTML= innerHTML.replaceAll('\n', '')
 *    注意：依赖下面replaceWithElement函数
 * @param event
 * @returns 选中的文字
 */
export function delimitation(event){
  /* //a simple replace
  let ele = event.target, type = window.getSelection().type;
  let start = window.getSelection().anchorOffset - 3, end = window.getSelection().focusOffset - 3, fulltext = ele.innerText;
  console.log(window.getSelection().getRangeAt(0).startContainer);
  console.log(window.getSelection().getRangeAt(0).endContainer);
  if(type == 'Range'){
    let selText = (window.getSelection ? window.getSelection() : document.selection.createRange().text).toString();
    console.log(ele.innerHTML.indexOf(selText));
    ele.innerHTML = ele.innerHTML.replaceAll(selText, '<span style="color: red;">' + selText + '</span>');
  }*/
  let range = window.getSelection().getRangeAt(0), rangeType = window.getSelection().type;
  if(rangeType != 'Range')
    //未选择有效区间
    return '';
  if(range.startContainer.parentNode != range.endContainer.parentNode)
    //禁止跨p节点
    return '';
  /* 注：此方式仅能正确获取区域字符串,偏移量切勿取此处！ */
  let ele = event.target, selTxt = (window.getSelection ? window.getSelection() : document.selection.createRange().text).toString();
  let startOffset = range.startOffset, endOffset = range.endOffset;
  let childNodes = ele.childNodes;
  /* 单标签下首次标红处理 */
  if(childNodes.length == 1){
    let nodeText = childNodes[0].textContent.trim();
    let prefix = nodeText.substring(0, startOffset);
    let middle = '<span style="color: red;">' + nodeText.substring(startOffset, endOffset) + '</span>';
    let suffix = nodeText.substring(endOffset, nodeText.length);
    ele.innerHTML = prefix + middle + suffix;
  }
  if(childNodes.length > 1){
    /* 所选区域未跨节点 */
    if(range.startContainer == range.endContainer){
      for (let index1 = 0; index1 < childNodes.length; index1++) {
        if (childNodes[index1] == range.startContainer ||
          childNodes[index1] == range.startContainer.parentNode){
          let nodeText = childNodes[index1].textContent;
          let prefix = nodeText.substring(0, startOffset);
          let middle = '<span style="color: red;">' + nodeText.substring(startOffset, endOffset) + "</span>";
          let suffix = nodeText.substring(endOffset, nodeText.length);
          // $(childNodes[index]).replaceWith( prefix + middle + suffix);
          replaceWithElement(ele, prefix + middle + suffix, index1);
        }
      }
    }else{
      let isStart = false;
      let repalce_span = ""
      for(let index2 = 0; index2 < childNodes.length; index2++){
        if (childNodes[index2] == range.startContainer ||  childNodes[index2] == range.startContainer.parentNode){
          isStart = true;
          let nodeText = childNodes[index2].textContent, type = childNodes[index2].nodeName.toLowerCase();
          let prefix;
          prefix = type == 'span' ? '<span style="color: red;">' + nodeText.substring(0, startOffset) + '</span>' : nodeText.substring(0, startOffset);
          let suffix = '<span style="color: red;">' + nodeText.substring(startOffset, nodeText.length);
          repalce_span =  prefix + suffix;
          childNodes[index2].replaceWith('');
        }else if(childNodes[index2] == range.endContainer ||
          childNodes[index2] == range.endContainer.parentNode){
          isStart = false;
          let nodeText = childNodes[index2].textContent, type = childNodes[index2].nodeName.toLowerCase();
          let prefix = nodeText.substring(0, endOffset) + "</span>";
          let suffix;
          suffix = type == 'span' ? '<span style="color: red;">' + nodeText.substring(endOffset, nodeText.length) + "</span>" : nodeText.substring(endOffset, nodeText.length);
          repalce_span += prefix + suffix;
          replaceWithElement(ele, repalce_span, index2);
          // $(childNodes[index]).replaceWith(repalce_span);
          break;
        }else{
          if(isStart){
            repalce_span += childNodes[index2].textContent;
            childNodes[index2].replaceWith('');
          }
        }
      }
    }
  }
  return selTxt;
}

/**
 * 替换jQuery_replaceWith
 * @param element  替换父元素
 * @param newCon   替换内容
 * @param childIndex  是否指定仅替换某个指定孩子节点,number,默认替换全部
 */
export function replaceWithElement(element ,newCon, childIndex){
  if(typeof childIndex != 'number')
    childIndex = false;
  let childNodes = element.childNodes, context = '';
  for (let i = 0; i < childNodes.length; i++){
    if(childIndex == i) {
      context += newCon;
      continue;
    }
    context += childNodes[i].outerHTML || childNodes[i].textContent;
  }
  element.innerHTML = (typeof childIndex == 'number' ? context : newCon);
}

export function goTop() {
  let timer = setInterval(function () {
    let left = window.pageYOffset;
    let step = Math.ceil((left - 0) / 10);
    window.scroll(0, left - step);
    if (left == 0) {
      clearInterval(timer);
    }
  }, 30)
}

export function CNDate(strDate, isIgnoreZero){
  if(!strDate) return '';
  let elist = ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
  // let upperNumList = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
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
    if(t >= '0' && t <= '9')
      cn_date += elist[parseInt(t)];
    else
      continue;
  }
  if(isIgnoreZero){
    let temp = cn_date.split('年');
    for (let i = 0; i < elist.length - 1; i++) {
      if(temp[1].indexOf('〇' + elist[i + 1]) > -1)
        temp[1] = temp[1].replaceAll('〇' + elist[i + 1], elist[i + 1]);
    }
    cn_date = temp[0] + '年' + temp[1];
  }
  return cn_date;
}

export function blobDownload(name, blob){
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
}

export function getKeys(){
  let _this = this;
  this.API.dataSearch('/api/productdata/get_match_show/' + this.query.sysId + '/' + this.query.tableId + '/2').then(function (response){
    let list;
    if(response.data.code == '1' && !!response.data.dataList && response.data.dataList.length > 0 ){
      list = response.data.dataList;
      _this.groupKeys = [];
      _this.groupDataTemp = [];
      for (let i = 0; i < list.length; i++)
        _this.groupKeys.push(list[i]['fieldKey'])
      _this.group(_this.groupKeys, 0);
    }
  });
}

/**
 * @param keys    需要分组的字段数组
 * @param flag    0
 */
export function group(keys, flag){

  if(!(!!keys) || keys.length == 0)
    return;

  let queryModel = this.setQueryModel(), f = flag;
  queryModel.setStart(-1);
  queryModel.setLength(-1);
  queryModel.newSrchGroup(keys[flag], -1, 0);

  let _this = this;
  this.API.dataSearch('/api/dataSearch/getGroupKData?data_type=1', queryModel.toJson()).then(function (response){
    if(!!response.data.dataList && !!response.data.dataList.length > 0)
      _this.groupDataTemp[keys[f]] = response.data.dataList;
    /* 执行结束 将缓存添加至groupData */
    if(flag == keys.length - 1)
      _this.groupData = _this.groupDataTemp;
    /* 依次对每个字段进行分组 */
    keys.length > flag + 1 && _this.group(keys, ++flag);
  });
}