package com.example.housing.service;

import com.example.housing.exception.MemberNotExists;
import com.example.housing.model.Book;
import com.example.housing.model.Member;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

public class MemberLibraryService {
    private final HashSet<Member> membersRecord;

    public MemberLibraryService() {
        membersRecord = new HashSet<>();
    }

    public void addMember(Member member) {
        membersRecord.add(member);
    }
    public void removeMember(Member member) {
        membersRecord.remove(member);
    }

    public List<Member> getMembersRecord() {
        return new ArrayList<>(membersRecord);
    }


    public void updateMemberRecord(int id, String name, String password, HashSet<Book> borrowedBook) {
        try{
            for(Member m : membersRecord) {
                if(m.getMemberID()==id) {
                    membersRecord.remove(m);
                    Member updatedDetails=new Member(id, name, password, borrowedBook);
                    membersRecord.add(updatedDetails);
                }

            }
            throw new MemberNotExists("No records found");
        }catch(MemberNotExists e) {
            System.out.println(e);
        }
    }

    public String removeMemberRecord(int id) {
        try {
            for (Member m : membersRecord) {
                if (m.getMemberID() == id) {
                    membersRecord.remove(m);
                    return "success";
                }
            }
            throw new MemberNotExists("No records found");
        }catch(MemberNotExists e) {
            System.out.println(e);
        }
        return "Attempt Failed";
    }

    public List<Member> sortMembersByID() {
        return membersRecord.stream()
                .sorted(Comparator.comparingInt(Member::getMemberID)) // Sorting by Member ID
                .collect(Collectors.toList());
    }

    // Sort members by name
    public List<Member> sortMembersByName() {
        return membersRecord.stream()
                .sorted(Comparator.comparing(Member::getName)) // Sorting by Member Name
                .collect(Collectors.toList());
    }

}
