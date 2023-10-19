<template>
  <div class="delimitation_area" v-html="content" @mouseup="delimitationEvent"></div>
</template>

<script>
export default {
  name: "delimitation",
  props: {
    content: {
      type: String,
      default: ''
    },
    color: {
      type: String,
      default: 'red'
    },
  },
  data(){
    return {}
  },
  methods: {
    delimitationEvent(event){
      this.$emit('delimitationEvent', this.delimitation(event));
    },
    /**
     * 划词标红,绑定前需要清除节点内空格等无效数据
     *    如: innerHTML= innerHTML.replaceAll('\n', '')
     * @param event
     * @returns 选中的文字
     */
    delimitation(event){
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
        let middle = '<span style="color: ' + this.color + ';">' + nodeText.substring(startOffset, endOffset) + '</span>';
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
              let middle = '<span style="color: ' + this.color + ';">' + nodeText.substring(startOffset, endOffset) + "</span>";
              let suffix = nodeText.substring(endOffset, nodeText.length);
              // $(childNodes[index]).replaceWith( prefix + middle + suffix);
              this.replaceWithElement(ele, prefix + middle + suffix, index1);
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
              prefix = type == 'span' ? '<span style="color: ' + this.color + ';">' + nodeText.substring(0, startOffset) + '</span>' : nodeText.substring(0, startOffset);
              let suffix = '<span style="color: ' + this.color + ';">' + nodeText.substring(startOffset, nodeText.length);
              repalce_span =  prefix + suffix;
              childNodes[index2].replaceWith('');
            }else if(childNodes[index2] == range.endContainer ||
                childNodes[index2] == range.endContainer.parentNode){
              isStart = false;
              let nodeText = childNodes[index2].textContent, type = childNodes[index2].nodeName.toLowerCase();
              let prefix = nodeText.substring(0, endOffset) + "</span>";
              let suffix;
              suffix = type == 'span' ? '<span style="color: ' + this.color + ';">' + nodeText.substring(endOffset, nodeText.length) + "</span>" : nodeText.substring(endOffset, nodeText.length);
              repalce_span += prefix + suffix;
              this.replaceWithElement(ele, repalce_span, index2);
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
    },

    /**
     * 替换jQuery_replaceWith
     * @param element  替换父元素
     * @param newCon   替换内容
     * @param childIndex  是否指定仅替换某个指定孩子节点,number,默认替换全部
     */
    replaceWithElement(element ,newCon, childIndex){
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
  }
}
</script>

<style scoped>

</style>