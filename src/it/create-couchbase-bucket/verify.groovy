// make sure the bucket has been created by attempting to delete it (serves as clean-up operation as well)
def curlOutput = "curl -i -X DELETE -u Administrator:couchbase http://localhost:8091/pools/default/buckets/test-couchbase-bucket".execute().text
assert curlOutput.contains("HTTP/1.1 200 OK")

// simple check for now - verify that the build log says the bucket has been created
File buildLog = new File(basedir, 'build.log')
assert buildLog.exists()

assert buildLog.text.contains("[INFO] Created bucket 'test-couchbase-bucket'")
