// make sure the bucket has been deleted by attempting to delete it again (serves as clean-up operation as well)
def curlOutput = "curl -i -X DELETE -u Administrator:couchbase http://localhost:8091/pools/default/buckets/some-real-bucket".execute().text
assert curlOutput.contains("HTTP/1.1 404 Object Not Found")

// simple check for now - verify that the build log says the bucket has been deleted
File buildLog = new File(basedir, 'build.log')
assert buildLog.exists()

assert buildLog.text.contains("[INFO] Deleted bucket 'some-real-bucket'")
