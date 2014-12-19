#!/bin/bash

declare -a arr=("ec2-54-154-70-159.eu-west-1.compute.amazonaws.com" "ec2-54-77-70-125.eu-west-1.compute.amazonaws.com"
		"ec2-54-77-70-125.eu-west-1.compute.amazonaws.com" "ec2-54-154-66-19.eu-west-1.compute.amazonaws.com");

for i in "${arr[@]}"
do
  echo "$i"
  ssh -i ~/.ssh/tom_key.pem "$i" "hostname";
  ssh -i ~/.ssh/tom_key.pem "$i" "git clone https://github.com/tom-cb/cust-acct-demo.git";
  ssh -i ~/.ssh/tom_key.pem "$i" "wget http://nodejs.org/dist/v0.10.34/node-v0.10.34-linux-x64.tar.gz";
  ssh -i ~/.ssh/tom_key.pem "$i" "tar -zxvf http://nodejs.org/dist/v0.10.34/node-v0.10.34-linux-x64.tar.gz";
  ssh -i ~/.ssh/tom_key.pem "$i" "sudo yum install httpd24-tools-2.4.10-1.59.amzn1.x86_64";
  ssh -i ~/.ssh/tom_key.pem "$i" "~/cust-acct-demo/REST/start-rest-servers.sh";
  ssh -i ~/.ssh/tom_key.pem "$i" "~/cust-acct-demo/REST/start-ab.sh";
done
