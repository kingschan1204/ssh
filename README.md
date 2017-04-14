# ssh
springMvc+spring+hibernate ssh搭建

## 表结构
```
CREATE TABLE `ssh-users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sex` bit(1) NOT NULL,
  `age` tinyint(4) NOT NULL,
  `email` varchar(30) NOT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
