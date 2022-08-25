复选案由树组件

使用方法简介
    传递参数
        parameter               default                     description
        tableId                    '515'                        检索范围（默认为案由树）
        sysId                       '114'                       检索范围（默认为案由树）
        dataFilter                  ''                          格式: 'id;grade' 对应sys_code与class_grade
                                                                例：0009;1  条件 = sys_code = '0009' and class_grade = 1
        withSearch                  false                       是否支持筛选查询
        lazy                        false                       是否懒加载
        isAppointTreeData           false                       是否指定树数据
        appointTreeData             []                          树数据
        defaultCheckedList          []                          默认选中项id数组