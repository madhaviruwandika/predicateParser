data "aws_iam_instance_profile" "existing_role" {
  name = "ec2-ecr-access-role"
}