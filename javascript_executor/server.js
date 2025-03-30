const app = require("./src/main");


const server = app.listen(8000, () => {
    console.log(`Server listening on port ${8000}`);
})

process.on('SIGINT', () => {
    server.close(() => {
        console.log('Server closed');
        process.exit(0);
    })
})