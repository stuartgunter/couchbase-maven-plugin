// delete the bucket to clean-up
def curlOutput = "curl -i -X DELETE -u Administrator:couchbase http://localhost:8091/pools/default/buckets/test-duplicate-bucket".execute().text
assert curlOutput.contains("HTTP/1.1 200 OK")

// verify the build log contains the error messages from the HTTP response
File buildLog = new File(basedir, 'build.log')
assert buildLog.exists()

assert buildLog.text.contains("\tname:\tBucket with given name already exists")
