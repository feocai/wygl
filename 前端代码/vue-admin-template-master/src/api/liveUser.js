import http from '@/utils/http';

//新增
export async function saveLiveUserApi(param){
    return await http.post('/api/liveUser',param)
}
//获取角色列表
export async function getRoleListApi(){
    return await http.get('/api/role/getRoleList');
}
//根据id查询用户
export async function getUserByIdApi(param){
    return await http.get('/api/liveUser/getUserById',param);
}
//查询
export async function getListApi(param){
    return await http.get('/api/liveUser/list',param);
}
// 编辑
export async function editLiveUserApi(param){
    return await http.put('/api/liveUser',param)
}

//获取房屋列表
export async function getHouseListApi(parm){
    return await http.get('/api/houseList/list',parm);
}
//分配房屋保存
export async function saveAssignHouseApi(parm){
    return await http.post('/api/liveUser/assignHouse',parm);
}
//获取车位列表
export async function getParkListApi(parm){
    return await http.get('/api/parkList/list',parm);
}
//分配车位
export async function assignParkApi(parm){
    return await http.post('/api/liveUser/assignPark',parm);
}
//退房
export async function returnHouseApi(parm){
    return await http.post('/api/liveUser/returnHouse',parm);
}
//退车位
export async function returnParkApi(parm){
    return await http.post('/api/liveUser/returnPark',parm);
}

//删除
export async function deleteLiveUserApi(param){
    return await http.delete('/api/liveUser',param);
}