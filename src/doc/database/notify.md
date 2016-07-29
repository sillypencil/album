#通知表(notify)

- id
- notifyType            通知类型
- notifyContentId       通知相关的id（如喜欢和采集，评论, 也就是一对一关系）,需要注意，这个没有设置外键
- notifyUserId          执行通知的用户
- notifiedUserId        被通知的用户
- createTime            发送通知时间
- isNotify              是否已推送通知

###relation:
- 执行动作的用户    多对一
- 被通知的用户      多对一