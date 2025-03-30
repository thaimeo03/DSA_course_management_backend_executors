const express = require('express')
const morgan = require('morgan')
const router = require('./routes')

const app = express()

// Initialize middlewares
app.use(express.json())
app.use(morgan('dev'))


// Init routes
app.use('/', router)

// Error handler

module.exports = app