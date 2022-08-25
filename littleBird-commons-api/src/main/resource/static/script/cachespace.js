//Expand客户端缓存空间 start
//type0:循环队列,1或其他：算法空间
function CreateCacheSpace(size, type) {
    var space = new CacheSpace(size, type);
    return space;
}


function CacheSpace(size, type) {
    if (!isNaN(size)) {
        size = parseInt(size);
        if (size > 0) {
            size = size;
        }
    }
    else { size = 20; }
    this.size = size;
    this.list = new Array();
    this.length = 0;
    //this.oldPos = 0;
    //this.newPos = 0;
    this.pointer = 0;
    this.type = type;

}


CacheSpace.prototype = {
    //查询队列中是否存在此元素，存在返回下标，不存在返回-1
    find: function (ele, delFind) {
        var pos = this.length;
        var fFun = this.defaultFind;
        if (typeof delFind == "function") {
            fFun = delFind;
        }
        while (pos--) {
            if (fFun(this.list[pos], ele)) {
                return pos;
            }
        }
        return -1;
    },
    defaultFind: function (a, b) {
        return a == b;
    },
    enterSpace: function (ele) {
        if (typeof ele != "undefined") {
            if (this.type == 0) { //循环队列缓存放数据方法
                if (this.pointer >= this.size) {
                    this.pointer = 0;
                }

                this.list[this.pointer] = ele;
                this.pointer++;

            } else { //替换最小词频缓存数据方法
                if (this.pointer >= this.size) { //缓存满的情况下，替换检索频次最小的
                    var min = this.list[0].time;
                    var index = 0;
                    for (var i = 1; i < this.size; i++) {
                        if (this.list[i].time < min) {
                            min = this.list[i].time;
                            index = i;
                        }
                    }
                    this.list[index] = ele;
                } else { //缓存不满则继续放
                    this.list[this.pointer] = ele;
                    this.pointer++;
                }
            }

            if (this.length < this.size) {
                this.length++;
            }
            return true;
        }
        return false;
    },
    popSpace: function () {
        if (this.length > 0) {
            var ele = this.list[this.pointer];
            this.list--;
            this.length--;
            return ele;
        }
        return null;
    },
    //清空队列
    clearSpace: function () {
        this.length = 0;
        this.pointer = 0;
    },
    //判断队列是否为空
    isEmpty: function () {
        if (this.length == 0) {
            return true;
        } else {
            return false;
        }
    }
};

//Expand CircleSpace start
CacheSpace.prototype.exfind = function (ele) {
    return this.find(ele, function (kv) {
        return kv.key == ele;
    });
};
CacheSpace.prototype.pushSpace = function (k, v) {
    if (k && v) {
        this.enterSpace({ key: k, value: v, time: 1 });
    }
};
CacheSpace.prototype.getExData = function (key) {
    var temp = this.getEle(key);
    if (temp) {
        temp.time++; //从缓存取一次则检索频次+1
        return temp.value;
    }
    return "";
};
CacheSpace.prototype.getEle = function (key) {
    var pos = this.find(key, function (els, key) {
        if (els) {
            return els.key == key;
        }
        return false;
    });
    if (pos >= 0 && pos < this.length) {
        return this.list[pos];
    }
    return null;
};

//Expand客户端缓存空间 end