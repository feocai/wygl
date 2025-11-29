import http from "@/utils/http";

//分页查询
export async function getListApi(parm){
    return await http.get('/api/userComplain/getList',parm);
}
//添加
export async function addApi(parm){
    return await http.post('/api/userComplain',parm);
}
//修改
export async function editApi(parm){
    return await http.put('/api/userComplain',parm);
}
//删除
export async function deleteApi(parm){
    return await http.delete('/api/userComplain',parm);
}
//获取我的投诉列表
export async function getMyListApi(parm){
    return await http.get('/api/userComplain/getMyList',parm);
}