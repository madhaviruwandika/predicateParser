data "aws_iam_instance_profile" "existing_role" {
  name = var.ec2_role_name
}

data "aws_security_group" "existing_sg" {
  filter {
    name   = "group-name"
    values = ["allow_ssh_http"]
  }

  vpc_id = "vpc-025544307726fbc15"
}
