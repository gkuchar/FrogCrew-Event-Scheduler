const { defineConfig } = require('@vue/cli-service')
const path = require('path')

module.exports = defineConfig({
    transpileDependencies: [],
    publicPath: process.env.NODE_ENV === 'production'
      ? '/frogcrew-frontend/'
      : '/',
    pages: {
      index: {
        entry: 'frontend/main.js',
        template: 'public/index.html',
        filename: 'index.html'
      }
    },
    configureWebpack: {
      resolve: {
        alias: {
          '@': path.resolve(__dirname, 'frontend')
        }
      }
    },
    devServer: {
      port: 8083,
      host: 'localhost',
      allowedHosts: 'all',
      client: {
        webSocketURL: 'ws://localhost:8083/ws'
      }
    }
  })
  