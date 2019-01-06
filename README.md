# ec2-communication

This project creates a service that runs on two EC2 machines.
After sending an HTTP POST request to one of the instances, a subsequent HTTP GET request to any of those instances
should return the entity that was submitted in the first HTTP POST request.

Usage example:

First run:
<pre><code>curl -X POST -H "Content-Type: application/json" -d '{"something": "blabla"}' http://<some_instance>/api/resource</code></pre>

And then run:
<pre><code>curl http://<another_instance>/api/resource</code></pre>

Will print:
<pre><code>{"something": "blabla"}</code></pre>