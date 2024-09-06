provider "aws" {
  region = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

resource "aws_instance" "padicate_parser" {
  ami           = "ami-0182f373e66f89c85"  # Example Amazon Linux 2 AMI
  instance_type = "t2.micro"  # Choose your instance type
  key_name      = var.key_name  # SSH Key Pair

  user_data = <<-EOF
    #!/bin/bash
    # Update and install Docker
    sudo yum update -y
    sudo amazon-linux-extras install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user

    # Install AWS CLI
    sudo yum install -y aws-cli

    # Authenticate Docker with ECR
    aws ecr get-login-password --region ${var.aws_region} | docker login --username AWS --password-stdin ${var.aws_account_id}.dkr.ecr.${var.aws_region}.amazonaws.com

    # Pull the Docker image from ECR
    docker pull ${var.aws_account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/${var.ecr_repository}:latest

    # Run the Docker container
    docker run -d -p 8080:8080 ${var.aws_account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/${var.ecr_repository}:latest
  EOF

  tags = {
    Name = "predicate_parcer_instance"
  }

  # Security Group for SSH and HTTP access
  vpc_security_group_ids = [aws_security_group.allow_ssh_http.id]
}

resource "aws_security_group" "allow_ssh_http" {
  name_prefix = "allow_ssh_http"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "instance_public_ip" {
  value = aws_instance.padicate_parser.public_ip
}
