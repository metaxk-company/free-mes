let ranNum = function () {
  let str = 'QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789'
  return str
    .split('')
    .sort(function () {
      return Math.random() - 0.5
    })
    .join('')
    .substring(0, 69)
}

const autocannon = require('autocannon')

autocannon(
  {
    url: 'https:172.16.20.10/admin-api/mes/md/unit/measure/save',
    headers: {
      accept: 'application/json, text/plain, */*',
      'accept-language': 'zh-CN,zh;q=0.9',
      authorization: 'Bearer 30ebbceccb5b4baf967c386854722653',
      'content-type': 'application/json',
      'tenant-id': '1',
      Referer: 'http://localhost:3080/',
      'Referrer-Policy': 'strict-origin-when-cross-origin'
    },
    body: `{"measureName":"${ranNum()}","changeRate":0,"enableFlag":"Y","primaryFlag":"Y","remark":"","primaryId":"","id":""}`,
    method: 'POST',
    connections: 100000, // 连接数
    pipelining: 100, // 流水线数量
    duration: 5 // 持续时间
  },
  console.log
)
