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

export function addTask(data) {
    return request({
        url: '/pear/task/addTask',
        method: 'post',
        data: data
    })
}

export function addTaskWork(data) {
    return request({
        url: '/pear/task/addTaskWork',
        method: 'post',
        data: data
    })
}

export function listUserTaskWork() {
    return request({
        url: '/pear/task/listUserTaskWork',
        method: 'get'
    })
}