package com.project.elasticsearch.search.searchUtil;

import com.project.elasticsearch.search.SearchRequestDto;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class searchUtil {

    public static SearchRequest buildSearchRequest(final String indexName,
                                                   final SearchRequestDto dto) {
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(dto));

            if (dto.getSortBy() != null) {
                builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
            }

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (Exception exp) {
            exp.printStackTrace();
            return null;
        }

    }

    public static SearchRequest buildSearchRequest(final String indexName, String field, Date date) {

        try {
            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(field, date));

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildSearchRequest(final String indexName, SearchRequestDto dto, Date date) {

        try {
            QueryBuilder searchQuery = getQueryBuilder(dto);
            QueryBuilder dateQuery = getQueryBuilder("createdDate", date);

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(searchQuery).must(dateQuery);
            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .postFilter(boolQueryBuilder);

            if (dto.getSortBy() != null) {
                builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
            }

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static QueryBuilder getQueryBuilder(SearchRequestDto dto) {
        if (ObjectUtils.isEmpty(dto) && CollectionUtils.isEmpty(dto.getFields())) {
            return null;
        }

        List<String> fields = dto.getFields();

        if (dto.getFields().size() > 1) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);

            fields.forEach(multiMatchQueryBuilder::field);

            return multiMatchQueryBuilder;
        }

        return fields.stream().findFirst().map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm())
                        .operator(Operator.AND))
                .orElse(null);
    }

    public static QueryBuilder getQueryBuilder(String field, Date date) {
        return QueryBuilders.rangeQuery(field).gte(date);
    }
}
