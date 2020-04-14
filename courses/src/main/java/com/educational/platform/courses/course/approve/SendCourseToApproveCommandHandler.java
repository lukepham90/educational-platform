package com.educational.platform.courses.course.approve;

import com.educational.platform.common.exception.ResourceNotFoundException;
import com.educational.platform.courses.course.Course;
import com.educational.platform.courses.course.CourseAlreadyApprovedException;
import com.educational.platform.courses.course.CourseRepository;
import com.educational.platform.integration.events.SendCourseToApproveIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Command handler for {@link SendCourseToApproveCommand}, send a course to approve.
 */
@RequiredArgsConstructor
@Component
@Transactional
public class SendCourseToApproveCommandHandler {

    private final CourseRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Handles send course to approve command.
     *
     * @param command command
     * @throws CourseAlreadyApprovedException if course was already approved
     * @throws ResourceNotFoundException      if resource not found
     */
    public void handle(SendCourseToApproveCommand command) {
        final Optional<Course> dbResult = repository.findById(command.getId());
        if (dbResult.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Course with id: %s not found", command.getId()));
        }

        final Course course = dbResult.get();
        course.sendToApprove();
        eventPublisher.publishEvent(new SendCourseToApproveIntegrationEvent(course, command.getId()));
    }
}