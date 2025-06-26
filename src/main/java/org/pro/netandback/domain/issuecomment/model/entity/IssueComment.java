package org.pro.netandback.domain.issuecomment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.user.model.entity.User;

@Entity
@Table(name = "issue_comments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueComment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @Column(nullable = false)
    private String content;

    public void updateContent(String content) {
        this.content = content;
    }
}
