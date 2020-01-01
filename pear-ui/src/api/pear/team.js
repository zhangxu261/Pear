import request from '@/utils/request'

// 查询团队列表
export function listTeam(query) {
    return request({
        url: '/pear/team/list',
        method: 'get',
        params: query
    })
}

export function listMember(query) {
    return request({
        url: '/pear/team/listMember',
        method: 'get',
        params: query
    })
}

export function searchMember(query) {
    return request({
        url: '/pear/team/searchMember',
        method: 'get',
        params: query
    })
}

export function addMember(teamId, userId) {
    const data = {
        teamId,
        userId
    }
    return request({
        url: '/pear/team/addMember',
        method: 'post',
        params: data
    })
}

export function removeMember(teamId, userId) {
    const data = {
        teamId,
        userId
    }
    return request({
        url: '/pear/team/removeMember',
        method: 'post',
        params: data
    })
}

export function setMemberLeader(teamId, userId) {
    const data = {
        teamId,
        userId
    }
    return request({
        url: '/pear/team/setMemberLeader',
        method: 'post',
        params: data
    })
}

export function addTeam(data) {
    return request({
        url: '/pear/team',
        method: 'post',
        data: data
    })
}