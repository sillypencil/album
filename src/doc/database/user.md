#用户表(user)

- id
- account       登陆账号
- password      密码
- nickname      昵称
- avatarPath    头像路径
- followNum     关注人数
- fansNum       粉丝人数
- favorNum      喜欢相片数
- collectNum    采集数
- notifyNum     通知数
- registerTime  注册时间

###relation:

- 相册            一对多
- 评论            一对多
- 关注            多对多
- 喜欢的照片      多对多
- 采集的照片      多对多
- 评论            一对多
- 通知            一对多
