Mock.setup({
    timeout: 1000
});

Mock.mock(/\/search\/loan\?tag=(.*)&amount=(.*)&deadline=(.*)/, {
    "resultCode": 200,
    "resultMsg": "ok",
    "data|0-4": [
        {
            id: '@id()',
            name: '@cname()',
            logo: Mock.Random.image('40x40'),
            enableMoney: [1000, 2000, 3000, 4000, 5000],
            deadline: [1, 2, 3],
            deadlineUnit: '@pick("日","月")',
            interestRate: '0.05',
            fastestGetTime: 1,
            isHot: '@boolean()',
            isNew: '@boolean()',
            'tag|1-3': ['@ctitle(2, 6)']
        }
    ]
})