package com.educational.platform.administration.course.create;

import com.educational.platform.administration.course.CourseProposal;
import com.educational.platform.administration.course.CourseProposalRepository;
import com.educational.platform.common.domain.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command handler for {@link CreateCourseProposalCommand} creates a course proposal.
 */
@RequiredArgsConstructor
@Component
@Transactional
public class CreateCourseProposalCommandHandler implements CommandHandler {

    private final CourseProposalRepository courseProposalRepository;

    /**
     * Creates course proposal from command.
     *
     * @param command command
     */
    public void handle(CreateCourseProposalCommand command) {
        final CourseProposal courseProposal = new CourseProposal(command);
        courseProposalRepository.save(courseProposal);
    }

}
