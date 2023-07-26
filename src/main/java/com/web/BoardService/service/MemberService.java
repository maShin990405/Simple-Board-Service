package com.web.BoardService.service;

import com.web.BoardService.controller.MemberLoginDTO;
import com.web.BoardService.controller.MemberSignUpDTO;
import com.web.BoardService.domain.Member;
import com.web.BoardService.domain.Role;
import com.web.BoardService.repository.MemberRepository;
import com.web.BoardService.repository.RoleRepository;
import exceptions.*;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private static final int MIN_NICKNAME_LENGTH = 1;
    private static final int MAX_NICKNAME_LENGTH = 12;
    private static final int MIN_USERNAME_LENGTH = 6;
    private static final int MAX_USERNAME_LENGTH = 12;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final int MIN_ASCII_VAL = 33;
    private static final int MAX_ASCII_VAL = 126;
    Logger logger = LoggerFactory.getLogger(MemberService.class);

    /**
     * Process the signup request. If given member is validated, then this
     * function persists the member into db, and return his/her id. In case
     * of error, it throws the respective exception, and return -1 as an
     * error code.
     * @param member - A member trying to sign up with inputs.
     * @return id - An id of given member, else return error code(-1).
     */
    public Member join(@NotNull MemberSignUpDTO member) {
        logger.info("INFO: member is attempting to SIGN UP");
        Member memberSignup = new Member();
        memberSignup.setUsername(member.getUsername());
        memberSignup.setPassword(member.getPassword());
        memberSignup.setNickname(member.getNickname());
        memberSignup.setEmail(member.getEmail());
        memberSignup.setCreated_date(Instant.now().toString());
        memberSignup.setModified_date(Instant.now().toString());
        Role role = roleRepository.findByName("ROLE_USER");
        memberSignup.setRoles(Arrays.asList(role));


        if (validateMember(memberSignup)) {
            memberRepository.save(memberSignup);
            logger.info("INFO: member " + memberSignup.getUsername() + " has successfully SIGNUP");
            return memberSignup;
        }

        else {
            return null;
        }
    }

    /**
     * Process the login request. If given username and password matches
     * the data from db, then this function returns the member id. In case
     * of error, it throws the respective exception, and return -1 as an
     * error code.
     * @Param memberDTO - An object that contains inputted member information for login.
     * @return id - An id of member if the given username and password are
     *              validated, else return error code(-1).
     */
    public Member processLogin(@NotNull MemberLoginDTO memberDTO) {
        logger.info("INFO: user: " + memberDTO.getUsername() + " is attempting to LOGIN");
        Member member = findMemberByUsername(memberDTO.getUsername());

        if (isPasswordCorrect(member, passwordEncoder.encode(memberDTO.getPassword()))) {
            logger.info("INFO: user: " + memberDTO.getUsername() + " has successfully LOGIN");
            return member;
        }

        else {
            return null;
        }
    }

    /*
     * Below methods are helper methods that process the given member inputs
     * for the login.
     */
    private Member findMemberByUsername(String username) {
        Optional<Member> m = memberRepository.findByUsername(username);
        if (m.isPresent()) {
            return m.get();
        }
        else {
            throw new MemberInvalidInputException("Login failed: Check the " +
                    "username and password again.");
        }
    }

    private boolean isPasswordCorrect(@NotNull Member m, String password) {
        if (m.getPassword().equals(password)) {
            return true;
        }

        else {
            throw new MemberInvalidInputException("Login failed: Check the " +
                    "username and password again.");
        }
    }

    /*----------------- End of the login helper methods. ----------------*/

    /*
     * Below methods are helper methods that process the given member inputs
     * for the signup.
     */
    private boolean validateMember(Member member) {
        checkDuplicate(member);
        processUsername(member);
        processPassword(member);
        processEmail(member);
        processNickname(member);
        return true;
    }

    private void checkDuplicate(@NotNull Member member) {
        //Check if given member exists in the database
        logger.info("INFO: processing the member id for SIGNUP");
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                    throw new MemberAlreadyExistsException("Member Already Exists");
                });
    }

    private void processUsername(@NotNull Member member) {
        /* Check if given username meets the following conditions:
         * 1. A username is not taken
         * 2. Its length must be at least 6 characters, and at most 12 characters
         * 3. Must be combination of alphabet, number, and special character
         */
        logger.info("INFO: processing the username for SIGNUP");

        //Check the duplicate
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m -> {
                    throw new UsernameAlreadyExistsException("Username Already Exists");
                });

        //Check the length
        String username = member.getUsername();
        char[] process = username.toCharArray();

        if (process.length < MIN_USERNAME_LENGTH ||
                process.length > MAX_USERNAME_LENGTH) {
            throw new MemberInvalidInputException
                    ("A username must be at least 6 characters " +
                            "and at most 12 characters.");
        }
    }

    private void processPassword(@NotNull Member member) {
        logger.info("INFO: processing the password for SIGNUP");
        if (member.getPassword().length() < MIN_PASSWORD_LENGTH ||
                member.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new MemberInvalidInputException("A password must be at least " +
                    "8 characters and at most 32 characters.");
        }

        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasLetter = letter.matcher(member.getPassword());
        Matcher hasDigit = digit.matcher(member.getPassword());
        Matcher hasSpecial = special.matcher(member.getPassword());

        if (!hasLetter.find() || !hasDigit.find() || !hasSpecial.find()) {
            throw new MemberInvalidInputException("A password must contain at least " +
                    "1 lowercase and uppercase alphabet, 1 special character, and 1 number.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));
    }

    private void processEmail(@NotNull Member member) {
        //Check if given email is taken by other user.
        logger.info("INFO: processing the email for SIGNUP");
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new EmailAlreadyExistsException("Email Already Exists");
                });
    }

    private void processNickname(@NotNull Member member) {
        //Check if given nickname is taken by other user.
        logger.info("INFO: processing the nickname for SIGNUP");
        memberRepository.findByNickname(member.getNickname())
                .ifPresent(m -> {
                    throw new NicknameAlreadyExistsException("Nickname Already Exists");
                });

        /* Check if given nickname meets the following condition:
         * 1. Must be combination of alphabet, number, and special character
         * 2. Its length must be at least 1 character,
         *    and at most 12 characters
         */

        String nickname = member.getNickname();
        char[] process = nickname.toCharArray();

        if (process.length < MIN_NICKNAME_LENGTH ||
                process.length > MAX_NICKNAME_LENGTH) {
            throw new MemberInvalidInputException
                    ("A nickname must be at least 1 characters " +
                            "and at most 12 characters.");
        }

        for (char c : process) {
            if ((int)c < MIN_ASCII_VAL || (int)c > MAX_ASCII_VAL) {
                throw new MemberInvalidInputException
                        ("A nickname must be combination of alphabet, " +
                                "number, or special character");
            }
        }
    }

    /*----------------- End of the signup helper methods. ----------------*/
}
