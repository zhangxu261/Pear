import request from '@/utils/request'

export function listProject(query) {
    return request({
        url: '/pear/task/listProject',
        method: 'get',
        params: query
    })
}

export function listTask(query) {
    return request({
        url: '/pear/task/list',
        method: 'get',
        params: query
    })
}