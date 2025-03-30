'use strict'

const express = require('express')
const executorRouter = require('./executor')
const router = express.Router()

// Init routes
router.get('/hello', (req, res) => {
    return res.status(200).json({
        message: 'Hello World!'
    })
})

router.use('/executor', executorRouter)

module.exports = router