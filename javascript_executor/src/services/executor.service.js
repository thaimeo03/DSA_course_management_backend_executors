'use strict'

const fs = require('fs')
const path = require('path')
const { exec } = require('child_process');

class ExecutorService {
    // 1. Create folder to store code files
    // 2. Hash name of file based on user, problem and create file
    // 3. Write code to file and execute
    async execute({ code, userId, problemId }) {
        // 1
        const dirPath = path.join(__dirname, '../temp')
        if (!fs.existsSync(dirPath)) {
            fs.mkdirSync(dirPath)
        }

        // 2
        const filePath = path.join(dirPath, `${userId}-${problemId}.js`)
        
        // 3
        return new Promise((resolve, reject) => {
            fs.writeFile(filePath, code, (err) => {
                if (err) {
                    console.error('Error writing file:', err.message)
                    return reject(err.message)
                }

                exec(`node ${filePath}`, (err, stdout, stderr) => {
                    if (err) {
                        return reject(stderr)
                    }

                    return resolve(stdout || stderr)
                })
            })
        }).then((data) => {
            return data.trim()
        }).catch((err) => {
            throw err
        }).finally(() => {
            // Delete file after execution
            fs.unlinkSync(filePath)
        })
    }
}

module.exports = new ExecutorService()