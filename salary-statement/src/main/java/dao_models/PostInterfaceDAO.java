package dao_models;

import dto_models.PostRequest;
import salary_statement_models.Post;

import java.util.UUID;

public interface PostInterfaceDAO extends BaseInterfaceDAO<Post, PostRequest, UUID> {
}
