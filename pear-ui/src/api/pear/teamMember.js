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
        url: '/pear/member/list',
        method: 'get',
        params: query
    })
}

export function addTeam(data) {
    return request({
      url: '/pear/team',
      method: 'post',
      data: data
    })
  }