import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return await http.get('/api/userRepair/getList',parm);
}
//分页查询--我的维修
export async function getMyListApi(parm){
    return await http.get('/api/userRepair/getMyList',parm);
}
//新增
export async function addApi(parm){
    return await http.post('/api/userRepair',parm);
}
//编辑
export async function editApi(parm){
    return await http.put('/api/userRepair',parm);
}
//删除
export async function deleteApi(parm){
    return await http.delete('/api/userRepair',parm);
}