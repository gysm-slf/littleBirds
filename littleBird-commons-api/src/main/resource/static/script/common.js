export function timestampToTime(timestamp) {
  var date = new Date(timestamp) //时间戳为10位需*1000，时间戳为13位的话不需乘1000
  var Y = date.getFullYear() + '-'
  var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
  var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
  var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
  var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':'
  var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())
  return Y + M + D + h + m + s
}


export function buildQuery(query) {
  // var query={
  //   pageNo:2,
  //   pageSize:10,
  //   orderBy:'id asc',
  //   querys:[
  //     {query:'org',condition:'0731',type:'=',relate:''},
  //     {query:'keys',condition:'管理员',type:'like',relate:''},
  //     {query:'times',condition:1,condition2:10,type:'between',relate:''},
  //   ]
  // }
  //页码,页容,顺序，检索配置
  // querys的type有=；like;between；
  // querys的relate有and;or
  let map = {pageNo:1,pageSize:10,orderBy:'',querys:[],condition:'',type:''}
  if(!query.pageNo){
    map.pageNo=query.pageNo;
  }
  if(!query.pageSize){
    map.pageNo=query.pageSize;
  }
  if(!query.orderBy){// orderBy按顺序排,sql规则,用','分隔
    map.orderBy=query.orderBy;
  }
  if(!query.querys){
    map.querys=query.querys;
  }
  return JSON.parse(JSON.stringify(map));
}



// 过滤集合元素
export function setfilter(list, key1, value, key2) {
  var data = list.filter(function (item) {
    return item[key1] == value;
  })
  var list = [];
  if (!!key2) {
    data.forEach(function (e) {
      list.push(e[key2]);
    });
    return list;
  } else {
    return data;
  }
}

//js将对象数组中具有父子关系的数据换成树形结构

function treeObj(originObj){
  //对象深拷贝
  let obj ={};

  for (let key in originObj){
    var val = originObj[key];
    obj[key] = typeof val === 'object' ? arguments.call(val):val;
  }
  //对象新增children键值，用于存放子树
  obj['children'] = [];
  return obj;
}

//data：带转换成树形结构的对象数组
//attributes：对象属性
function toTreeData (data, attributes) {
  let resData = data;
  let tree = [];

  //找寻根节点
  for (let i = 0; i < resData.length; i++) {

    if (resData[i][attributes.parentId] === ''|| resData[i][attributes.parentId] === null) {
      tree.push( treeObj(resData[i]) );
      resData.splice(i, 1);
      i--;
    }
  }

  run(tree);

  //找寻子树
  function run(chiArr) {
    if (resData.length !== 0) {
      for (let i = 0; i < chiArr.length; i++) {
        for (let j = 0; j < resData.length; j++) {
          if (chiArr[i][attributes.id] === resData[j][attributes.parentId]){
            let obj = treeObj(resData[j]);
            chiArr[i].children.push(obj);
            resData.splice(j, 1);
            j--;
          }
        }
        run(chiArr[i].children);
      }
    }
  }

  return tree;

}

//list基础数组，sonId子级属性名，parentId父级属性名，
export function getParentList(list,sonId,parentId) {
  let data = list;
  // 属性配置信息
  let attributes = {
    id: sonId,
    parentId: parentId,
  };
  let treeData = toTreeData(data, attributes);
  return treeData;
}

//end
//递归修改当前分类下的子集分类无法选中状态
export function changeList(a,num){
  let list =[];
  a.num=num;
  if(a.children.length>0){
    a.children.forEach(it=>{
      //若父级disabled为true,子集依次同为true
      if(a.disabled){it.disabled=true}
      changeList(it,num+1);
      list.push(it);
    })
    a.children=list;
  }
  return a
}

/**
 * token xhr请求
 */
export function xmlHttpRequest(url, data, type){
  axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      let token = getToken();
      if (token) {
        config.headers['Authorization'] = 'Bearer ' + token,
            config.headers['Content-Type'] = 'multipart/form-data'
      }
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });
  return axios({
    baseURL: process.env.VUE_APP_BASE_API,
    url: url,
    method: type,
    data: data,
    timeout: 10000, // request timeout
    // responseType: "arraybuffer", //可以使用arraybuffer
  })
}

export function tokenValid(res){
  if (res.code === USER_HAS_VALID || res.code === 50012 || res.code === 50014 || res.code === 401) {
    // to re-login
    MessageBox.confirm('用户信息已过期，请重新登录。', '用户过期提示', {
      confirmButtonText: '重新登录',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      store.dispatch('user/logout').then(() => {
        location.reload()
      })
    })
    return false;
  }else{
    return true;
  }
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
 * 获取图片blob
 * */
export function loadImg(img, url){
  Object.defineProperty(Image.prototype, 'authsrc', {
    writable: true,
    enumerable: true,
    configurable: true
  })
  let request = new XMLHttpRequest();
  request.responseType = 'blob';
  request.open('get', url, true);
  if (store.getters.token) {
    // let each request carry token
    // ['X-Token'] is a custom headers key
    // please modify it according to the actual situation
    let token = getToken();
    if (token) {
      request.setRequestHeader('Authorization', 'Bearer ' + token);
    }
  }
  request.onreadystatechange = e => {
    if (request.readyState == XMLHttpRequest.DONE && request.status == 200) {
      img.src = URL.createObjectURL(request.response);
      img.onload = () => {
        URL.revokeObjectURL(img.src);
      }
    }
  };
  request.send(null);
}

/**
 * 为什么不直接使用同步呢？ 虽然axios不支持直接的同步方式 但是这不是已经使用了原生xhr了吗？
 *
 * 递归方式加载图片数组(间接实现同步方式) 前三个参数类型必须一致 且只能为string或Array
 * @param url           需包含文件名与类型，否则请调用对应重载函数 loadImgSync(url, fileName, fileType, uploadFileArr, srcArr, loadTimes)
 * @param uploadFileArr 存储服务器响应的并转换为File对象的数组
 * @param srcArr        对应File对象数组的适用src 如base64、blob对象
 *
 *  * 适用场景：
 *    key1 -->  img1
 *    key2 -->  img2
 *    key3 -->  img3
 * 处理结果:
 *    uploadFileArr[(File)img1,(File)img2,(File)img3]
 *    srcArr[(base64)(File)img1,(base64)(File)img2,(base64)(File)img3]
 */
export function  loadImgSync(url, uploadFileArr, srcArr) {
  if (typeof url == 'string') {
    loadImgSync(url, getFileName(url), getFileType(url), uploadFileArr, srcArr, 0);
    if (url instanceof Array) {
      let nArray = [], tArray = [];
      for (let i = 0; i < url.length; i++) {
        nArray.push(getFileName(url));
        tArray.push(getFileType(url));
      }
      loadImgSync(url, nArray, tArray, uploadFileArr, srcArr, 0);
    }
  }
}

export function loadImgSync(url, fileName, fileType, uploadFileArr, srcArr, loadTimes){
  if((url instanceof Array && fileName instanceof Array && fileType instanceof Array) || (typeof url == 'string' && typeof fileName == 'string' && typeof fileType == 'string')){
    Object.defineProperty(Image.prototype, 'authsrc', {
      writable: true,
      enumerable: true,
      configurable: true
    })
    let request = new XMLHttpRequest();
    request.responseType = 'blob';
    url instanceof Array ? request.open('get', url[loadTimes], true) : request.open('get', url, true);
    if (store.getters.token) {
      let token = getToken();
      if (token) {
        request.setRequestHeader('Authorization', 'Bearer ' + token);
      }
    }
    request.onreadystatechange = e => {
      if (request.readyState == XMLHttpRequest.DONE && request.status == 200) {
        let temp;
        url instanceof Array
            ? temp = new File([request.response], fileName[loadTimes], {type: 'image/' + fileType[loadTimes]})
            : temp = new File([request.response], fileName, {type: 'image/' + fileType});
        uploadFileArr.push({raw: temp});
        fileToBase64(temp, imgBase64 => {
          srcArr.push(imgBase64);
          if(url instanceof Array){
            //先执行后判断
            if(loadTimes < url.length - 1){
              loadTimes++;
              loadImgSync(url, fileName, fileType, uploadFileArr, srcArr, loadTimes);
            }else {
              loadTimes = 0;
            }
          }
        });
      }
    };
    request.send(null);
  }else {
    console.log('loadImg: illegal parameter,type must be string or array!');
  }
}

export function resetAppend(formData, key, value){
  if(formData.has(key)) formData.delete(key);
  formData.append(key, value);
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

export function getFileName(absolutePath) {
  return getSplitLast(absolutePath, '/');
}

export function getSplitLast(str, reg) {
  let tmp = str.split(reg);
  return tmp[tmp.length - 1];
}

export function getFileType(absolutePath) {
  return getSplitLast(absolutePath, '.');
}

export function getStaticPath(absolutePath){
  return getSplitLast(absolutePath, 'static/');
}