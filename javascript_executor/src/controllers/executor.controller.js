'use strict'

const executorService = require("../services/executor.service")

class ExecutorController {
    async executeCode (req, res, next) {
        try {
            const data = await executorService.execute(req.body)

            return res.status(200).json({ message: data })
        } catch (error) {
            return res.status(400).json({ message: error })
        }
    }
}

module.exports = new ExecutorController()