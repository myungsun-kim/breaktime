module.exports = {
  devServer: {
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/conference': {
        target: 'https://localhost:8443/'
      },
      '/auth': {
        target: 'https://localhost:8443/'
      },
      '/user': {
        target: 'https://localhost:8443/'
      },
    },
    historyApiFallback: true,
    hot: true
  },
  transpileDependencies: [
    'element-plus'
  ],
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}