# !/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import json
from urlparse import urlparse, parse_qs

import requests


headers = {
    'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 9_2_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13D15 MicroMessenger/6.3.13 NetType/WIFI Language/zh_CN',
    "Accept": " */*",
    "Accept-Charset": " utf-8, iso-8859-1, utf-16, *;q=0.7",
    "Accept-Language": " zh-CN",
    "Connection": " keep-alive",
    "X-Requested-With": " XMLHttpRequest"
}


def getappmsgext(key, uin, url):
    """
    关键在于key和uin的获取，通过微信电脑版发起一个对话，粘贴以下URL，然后用浏览器打开，获得地址栏中的URL，其中有key和uin参数
    https://mp.weixin.qq.com/s?__biz=MjM5NTE4Njc4NQ==&mid=405483849&idx=1&sn=cac39a93ea4058564cd81e6627060d2d#wechat_redirect
    
    :param key: 访问者的秘钥
    :param uin: 访问者的ID（base64编码）
    :param url: 需要抓取的文章链接
    :return: 
    """
    qs = dict((k, v if len(v) > 1 else v[0])
              for k, v in parse_qs(urlparse(url).query).iteritems())
    params = {
        "__biz": qs.get('__biz'),
        "mid": qs.get('mid'),
        "sn": qs.get('sn'),
        "idx": qs.get('idx'),
        "f": "json",
        "is_need_ad": 0,
        "key": key,
        "uin": uin,
    }
    api = 'http://mp.weixin.qq.com/mp/getappmsgext'
    print params
    resp = requests.post(api, headers=headers, params=params)
    json.dump(resp.json(), sys.stdout, indent=4, ensure_ascii=False)


article_url = 'https://mp.weixin.qq.com/s?__biz=MjM5NDAwMTA2MA==&mid=434466335&idx=1&sn=4b63bd2d8968487ad59abcf31b7c8b72'
key = '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000' # 96位
uin = '' #base64编码访问者ID
getappmsgext(key, uin, article_url)

"""
{
    "advertisement_num": 0,
    "advertisement_info": [],
    "appmsgstat": {
        "show": true,
        "is_login": true,
        "liked": false,
        "read_num": 100001,
        "like_num": 2512,
        "ret": 0,
        "real_read_num": 430727
    },
    "comment_enabled": 1,
    "reward_head_imgs": [],
    "base_resp": {
        "wxtoken": 221223423
    }
}
"""