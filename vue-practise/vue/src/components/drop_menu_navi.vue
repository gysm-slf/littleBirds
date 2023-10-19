<!-- 资源分类导航 -->
<!---todo wcc 20221029 只保留框架，数据全部在外层构造好以后送进来，这里只保留骨架和相关的触发事件，其余的内容送回去后再自行处理 -->
<template>
  <div id="adv-drop-menu" class="c_1" :tableId="tableId" :resourceId="curResource.resourceId" @mouseleave="leave">
    <div class="c_11">
      <!-- 左侧的下拉按钮 -->
      <div class="c_2 c_23" @mouseenter="stay">
        <div class="c_24">{{ title }}</div>
        <div class="c_25">
          <div class="c_26"></div>
          <div><i class="el-icon-arrow-down"></i></div>
        </div>
      </div>
      <!-- 右侧的资源入口 -->
      <div class="c_27">
        <span v-for="(hNavi) in hMenu" class="crumb_s">
          <router-link v-if="hNavi.type=='link'" :to="hNavi.to" :target="hNavi.target || '_blank'">{{ hNavi.title }}</router-link>
          <a v-else @click="hMenuClick(hNavi)">{{ hNavi.title }}</a>
          <span>|</span>
        </span>
        <!-- 合库为期刊导航，其余根据当前的资源灵活配置当前的导航 -->
        <span>
          <!-- todo wcc 20220919 如果使用 /navi 的情况下， 在生产环境或者测试环境会导致父级路由重构的情况错误，找不到对应的页面 -->
          <router-link target="_blank" :to="{ name:'navi',query:{sysid: sysId, resid: dynamicNavi.resourceId} }">
              {{ dynamicNavi.resourceName_navi }}
 		      </router-link>
          </span>
      </div>
    </div>

    <!-- todo wcc 对应于上方的 c_11 的下拉内容 -->
    <div class="c_3" v-show="visible">
      <!-- dropMenu：下拉菜单项 -->
      <ul class="ml" v-for="crossResource in dropMenu__" v-show="crossResource.crossResourceCode.toUpperCase() != 'SCDB'">
        <h4 class="c_31">
          <a v-if="crossResource.crossResourceName" @click="dropMenuResClick(crossResource)">{{ crossResource.crossResourceName }}</a>
        </h4>

        <li v-for="(res,resIndex) in crossResource.children">
          <p :class="{'noLabel':!crossResource.children&&resIndex==0}">
            <input v-show="isCrossResource&&res.type!='link'"
                   type="checkbox"
                   v-model="res.checked"
                   :disabled="getDisabled(res)"
                   @change="checkChange"/>
            <router-link v-if="res.type=='link'" :to="res.to" :target="res.target || '_blank'">{{ res.resourceName }}</router-link>
            <a v-else @click="dropMenuResClick(res)">{{ res.resourceName }}</a>
          </p>
        </li>
      </ul>
      <div class="buttons" v-if="isCrossResource">
        <ul>
          <li id="clearBtn" @click="clear">{{ t('info.lable_clear') || '清空' }}</li>
          <li id="resetBtn" @click="reset">{{ t('info.btn_reset2') || '重置' }}</li>
          <li id="checkAllBtn" @click="checkAll">{{ t('info.lable_allcheck') || '全选' }}</li>
        </ul>
      </div>
    </div>

  </div>
</template>
<script>

export default {
  name: 'drop_menu_navi',
  props: {
    resCode: {//当前资源，决定展示标题与是否显示复选框
      type: String,
      default: 'scdb'
    },
    dropMenu: {//下拉导航菜单
      type: Array,
      default: () => []
    },
    hMenu: {//右侧横向菜单
      type: Array,
      default: () => []
    }
  },
  model: {
    prop: 'resCode',
    event: 'update:resCode'
  },
  watch: {
    dropMenu(dropMenu) {
      this.dropMenu__ = dropMenu;
      this.dropMenuInit();
    },
  },
  data() {
    return {
      name: 'dropMenuNavi',
      //description: '产品下拉导航组件',
      resCode__: this.resCode,
      dropMenu__: this.dropMenu,
      visible: false,
      curResource: {children: []},
      dynamicNavi: {}//资源的动态导航 若不存在则默认为期刊导航链接
    };
  },
  computed: {
    title() {
      return this.curResource.crossResourceName || this.curResource.resourceName;
    },
    isCrossResource() {//是否展示复选框 在第一层节点处加个grade属性 如果点击的 children（nodes）为空 则value.splice(i, 1, '') 若value长度为1 && 该值为false则表示 当前资源为单资源
      return this.curResource.children instanceof Array && this.curResource.children.length > 0
        || (this.curResource.resourceCode || '').toUpperCase() == this.resourceUtils.CODE_SCDB.toUpperCase()
        || (this.curResource.crossResourceCode || '').toUpperCase() == this.resourceUtils.CODE_SCDB.toUpperCase();
    },
  },
  created() {
    let allRes = [];
    this.dropMenu__.forEach(res => {
      res.children instanceof Array && (allRes = allRes.concat(res.children));
    });
    this.allRes = allRes;
    this.dropMenuInit();
  },
  methods: {
    dropMenuInit (){
      const _this = this;
      // ---------------------------------------------- 初始化tableId ---------------------------------------------- \\
      let _tableIds = [], curTableId;
      this.dropMenu__.forEach(crossRes => {//初始化合库tableId
        (_this.allRes || []).forEach(res => {
          if ((_this.arrContain(crossRes.default || [], res.resourceMark)
            || _this.arrContain(crossRes.default || [], res.productResourceCode)
            || _this.arrContain(crossRes.default || [], res.resourceCode)) && _tableIds.indexOf(res.tableId) == -1) {
            _tableIds.push(res.tableId);
          }
        });
        crossRes.tableId = _tableIds.join(',');
        _tableIds.length = 0;
      });
      !!(curTableId = this.$route.query.t) && (this.tableId = curTableId)
      // ---------------------------------------------- 初始化tableId ---------------------------------------------- \\
      this.selectResource(this.getCurResource());
      this.$emit('resource-switch', this.tableId = this.curResource.tableId);
    },
    selectResource(resource) {
      if (!!resource.crossResourceName) {//初始化合库默认检索资源
        const _this = this;
        this.dropMenu__.forEach(crossRes => {
          if (_this.resCode__.toUpperCase() == crossRes.crossResourceCode.toUpperCase()) {
            (_this.allRes || []).forEach(res => {
              if (_this.arrContain(crossRes.default || [], res.resourceMark)
                  || _this.arrContain(crossRes.default || [], res.productResourceCode)
                  || _this.arrContain(crossRes.default || [], res.resourceCode)) {
                _this.$set(res, 'checked', true);
              } else {
                _this.$set(res, 'checked', false);
              }
            });
          }
        });
      };
    },
    getCurResource() {
      let _this = this, res = this.dropMenu__.filter(crossRes => crossRes.crossResourceCode.toUpperCase() == _this.resCode__.toUpperCase());
      if (res.length == 1) { //当前为合库
        res = res[0];
      } else {
        res = [];
        this.dropMenu__.forEach(crossRes => (crossRes.children || []).forEach(r => {
          (r.resourceCode.toUpperCase() == _this.resCode__.toUpperCase()) && (res.push(r));
        }));
        if (res.length > 1) {//匹配到多个资源 如博硕、标准、专利，则需要进一步区分
          if (!!_this.tableId && _this.tableId.indexOf(',') == -1) {//携带参数t且为单资源 如博士  adv/cdmd/advsearch?sysid=113&tableId=539
            res = res.filter(res => {
              return res.resourceCode.toUpperCase() == _this.resCode__.toUpperCase() && res.tableId == _this.tableId;
            })[0];
          } else {//如博硕 未传tableId adv/cdmd/advsearch?sysid=113 此时t参数无效 可以不传
            res = this.resourceFormatter.filter(r => {
              return r.resourceCode.toUpperCase() == _this.resCode__.toUpperCase() && r.childResourceMsg instanceof Array && r.childResourceMsg.length > 0
            });
            res.length == 1 && (res = this.cloneByJson(res[0]))
          }
        } else if (res.length == 1) {
          res = res[0];
        }
      };
      this.curResource = res;//当前的导航资源 dropMenu菜单中的cur
      const curResource = (this.resourceFormatter.filter(r => (r.resourceCode.toLowerCase() == _this.resCode__))[0] || {});//获取未更改过的原始资源配置新
      this.dynamicNavi = !!curResource.resourceName_navi ? curResource : this.resourceFormatter.filter(r => (!!r.sysId && r.resourceCode.toUpperCase() == _this.resourceUtils.CODE_CJFQ.toUpperCase()))[0];//如果当前资源有导航，则显示对应的导航，如果没有的情况下，则使用期刊导航作为默认的导航入口
      return res;
    },
    stay() {
      this.visible = true;
    },
    leave() {
      this.visible = false;
    },
    dropMenuResClick(resource) {
      this.$emit('update:resCode', this.resCode__ = (resource.crossResourceCode || resource.resourceCode));
      this.$emit('resource-switch', this.tableId = resource.tableId);
      this.selectResource(this.getCurResource());
    },
    hMenuClick(resource) {
      let target_ = this.allRes.concat(this.dropMenu__).filter(res => res.resourceMark.toUpperCase() == resource.resourceCode.toUpperCase() || res.productResourceCode.toUpperCase() == resource.resourceCode.toUpperCase() || res.resourceCode.toUpperCase() == resource.resourceCode.toUpperCase()).length == 1 && this.dropMenuResClick(target_[0]);
    },
    getDisabled(childRes) {
      return !this.arrContain(childRes.crossResourceParent || [], this.resCode__);
    },
    checkChange (){
      let _tableIds = [];
      this.dropMenu__.forEach(cRes => (cRes.children || []).forEach(res => {
        res.checked && (_tableIds.push(res.tableId));
      }))
      this.$emit('cross-resource-range-change', _tableIds.join(','));
    },
    clear() {
      (this.allRes || []).forEach(res => {
        res.checked = false;
      });
      this.$emit('cross-resource-range-change', '');
    },
    reset() {
      const _this = this
      this.dropMenu__.forEach(crossRes => {
        if (_this.resCode__.toUpperCase() == crossRes.crossResourceCode.toUpperCase()) {
          (_this.allRes || []).forEach(res => {
            if (_this.arrContain(crossRes.default || [], res.resourceMark)
                || _this.arrContain(crossRes.default || [], res.productResourceCode)
                || _this.arrContain(crossRes.default || [], res.resourceCode)) {
              res.checked = true;
            } else {
              res.checked = false;
            }
          });
        }
      });
      this.$emit('cross-resource-range-change', this.dropMenu__.filter(croRes => croRes.crossResourceCode.toUpperCase() == _this.resCode__.toUpperCase())[0].tableId);
    },
    checkAll() {
      const _this = this;
      let _tableIds = [];
      (this.allRes || []).forEach(res => {
        if (_this.arrContain(res.crossResourceParent || [], _this.resCode__)) {
          res.checked = true;
          _tableIds.push(res.tableId);
        } else {
          res.checked = false;
        }
      });
      this.$emit('cross-resource-range-change', _tableIds.join(','));
    },
    /**
     * 功能描述:　数组是否包含参数txt字符串（忽略被包含内容大小写）
     * @author: slf
     * @date: 2023/2/9 10:38
     */
    arrContain(arr, txt) {
      return !!txt && (arr.indexOf(txt.toLowerCase()) > -1 || arr.indexOf(txt.toUpperCase()) > -1);
    }
  }
};
</script>

<style>
.c_1 {height: 55px;width: 79%;margin-bottom: 2px;float: right;border: 1px #F3F6FB solid;border-bottom: 1px #66B3FF solid;padding-bottom: 2px;z-index: 999;}
.c_11 {display: flex;}
.c_2 {cursor: pointer;border-top: 2px #1b66e6 solid;border-left: 1px #1b66e6 solid;border-right: 1px #1b66e6 solid;border-bottom: 1px #66B3FF solid;border-radius: 12px 12px 1px 1px;margin-bottom: 1px;background-color: #fff;}
.c_23 {width: 20%;height: 100%;line-height: 55px;display: flex;}
.c_24 {width: 90%;text-align: center;font-size: 18px;font-weight: bold;}
.c_25 {width: 10%;text-align: center;display: flex;align-items: center;justify-content: center;}
.c_26 {width: 1px;height: 35px;background: #d0d0d0;}
.c_3 {position: absolute;min-width: 680px;height: auto;background: #fff;z-index: 999;border: #66B3FF 1px solid;}
.c_3 ul.ml {float: left;width: 150px;margin-left: -1px;padding: 5px 0 5px 20px;border-left: 1px dashed #d7e8f1;zoom: 1;cursor: pointer;margin-top: 0px;}
.c_3 ul.ml li {margin-top: 10px;margin-bottom: 10px;}
.c_31 {font-size: 15px;font-weight: bold;color: #7B7B7B;margin-bottom: -3px;}
.c_27 {padding-left: 2px;}
.c_25 .el-icon-arrow-down {padding: 0px 6px;}
.noLabel {margin-top: -8px;}
.p_right30 ._group_cls_num { /* float: right;  */display: inline-block;vertical-align: middle;cursor: pointer;color: #999; /* margin-top: 4px; display: inline-block;line-height: 26px; */}
/* 左侧：悬停框 */
.c_23 {width: unset;}
.c_24 {width: unset;margin: auto 15px;min-width: 72px;}
.c_25 {width: unset;}
/* 右侧：论文、案例、法规、合同、业务助手、出版物导航 */
.c_11 {display: unset;}
.c_2 {float: left;}
.c_27 {height: 55px;line-height: 55px;float: right;}
.c_3 {margin-top: 55px;padding-bottom: 20px;}
/* 悬停下拉菜单的样式 */
.c_31 {min-height: 25.5px;}
ul.ml input[type="checkbox"] {margin-right: 3px;}
ol, ul li {list-style: none outside none;}
.c_3 li, .c_3 h4 {text-align: left;}
/* 按钮组要清楚 ml 的浮动，否则就回导致，在buttons中的几个按钮也 c_3 下的 ml 一样 浮动 */
.buttons {margin-right:40px;clear:both;}
.buttons ul li{float:right;margin:auto 20px;color:#fff;padding: 0px 10px;line-height: unset;border-radius: 5px;cursor: pointer;}
#checkAllBtn{background-color:#389AFF;}
#resetBtn{background-color:#E9B876;}
#clearBtn{background-color:#DCDBD9;}
</style>
