const express = require('express')
const executorRouter = express.Router()
const executorController = require('../../controllers/executor.controller')

// Execute code
executorRouter.post('/run', executorController.executeCode)


module.exports = executorRouter