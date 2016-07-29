#照片表（image）

- id
- imagePath     照片地址
- favorNum      被喜欢数量
- collectNum    被采集数量
- ownAlbumId    所属相册id
- createTime    创建时间

###relation:

- 相册            多对一
- 用户（喜欢）    多对多
- 用户（采集）    多对多
