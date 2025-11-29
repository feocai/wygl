import http from "@/utils/http";

//获取菜单列表
export async function getMenuListApi(){
    return await http.get('/api/menu/list');
}
//新增菜单
export async function addMenuApi(parm){
    return await http.post('/api/menu',parm);
}

//获取上级菜单树
export async function getParentTreeApi(){
    return await http.get('/api/menu/parent');
}

//修改菜单
export async function editMenuApi(parm){
    return await http.put('/api/menu',parm);
}
//删除菜单
export  async function deleteMenuApi(parm){
    return await http.delete('/api/menu',parm);
}