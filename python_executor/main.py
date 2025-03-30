from fastapi import FastAPI, HTTPException, status
from pydantic import BaseModel
import subprocess
import os

# Define the request body schema
class CodeRequest(BaseModel):
    code: str
    problemId: str
    userId: str

class UnicornException(Exception):
    def __init__(self, name: str, status: int):
        self.name = name
        self.status = status

app = FastAPI()

# 1. Get the code, problemId and userId from the request
# 2. Create a directory to store the Python file
# 3. Create a unique filename
# 4. Write code to file and execute
@app.post("/executor/run")
async def execute_code(request: CodeRequest):
    # 1
    code, problemId, userId = request.code, request.problemId, request.userId

    # 2
    dir_path = f"./tmp"

    if not os.path.exists(dir_path):
        os.makedirs(dir_path)

    # 3
    file_path = os.path.join(dir_path, userId + "_" + problemId + ".py")
    
    # 4
    try:
        # Save the Python code to a file
        with open(file_path, "w") as code_file:
            code_file.write(code)
        
        # Execute the Python file
        process = subprocess.run(
            ["py", file_path], capture_output=True, text=True
        )

        # Capture the output and error
        output = process.stdout
        error = process.stderr
        
        # Return the result
        if process.returncode != 0:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail=error)
        
        return {"message": output}
    
    except Exception as e:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail=str(e))
    
    finally:
        # Clean up the temporary file
        if os.path.exists(file_path):
            os.remove(file_path)