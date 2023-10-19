export default {
  data() {
    return {
      WebsiteConfig: {
        name: 'CNKI',
        limitConfig: {
          upload: {
            image: {
              max: '-1',
              min: '-1',
              allow_types: ['jpg', 'png']
            },
            audio: {
              max: '-1',
              min: '-1',
              allow_types: ['mp3', 'flex']
            },
            video: {
              max: '-1',
              min: '-1',
              allow_types: ['mp4', 'mkv']
            },
            doc: {
              max: '-1',
              min: '-1',
              allow_types: ['doc', 'txt']
            },
          },
          download: {
            image: {
              max: '-1',
              min: '-1',
              allow_types: ['jpg', 'png']
            },
            audio: {
              max: '-1',
              min: '-1',
              allow_types: ['mp3', 'flex']
            },
            video: {
              max: '-1',
              min: '-1',
              allow_types: ['mp4', 'mkv']
            },
            doc: {
              max: '-1',
              min: '-1',
              allow_types: ['doc', 'txt']
            },
          }
        }
      },
      query: {
        productCode: 'clkd8',
        sysId: '113',
        sType: '0',
        resId: '',
        tableId: '',
        conds: {},
        sentenceConds: [],
        order: [],
        date: [],
        pageNum: 1,
        pageSize: 20,
        total: 0,
        callbacks: null
      },
    }
  },
  created() {

  },
  methods: {
  }
}
