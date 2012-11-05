// make sure the bucket still does not exist by attempting to delete it
def curlOutput = "curl -i -X DELETE -u Administrator:couchbase http://localhost:8091/pools/default/buckets/some-fake-bucket".execute().text
assert curlOutput.contains("HTTP/1.1 404 Object Not Found")

// simple check for now - verify that the build log says the bucket has been deleted
File buildLog = new File(basedir, 'build.log')
assert buildLog.exists()

assert buildLog.text.contains("[INFO] Unable to delete bucket 'some-fake-bucket'")
