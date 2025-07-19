package jnu_ddobuk.fruitsfarm_BE.admin;

import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import jnu_ddobuk.fruitsfarm_BE.habittracker.service.HabitTrackerService;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import jnu_ddobuk.fruitsfarm_BE.member.repository.MemberRepository;
import jnu_ddobuk.fruitsfarm_BE.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final MemberService memberService;
    private final HabitTrackerService habitTrackerService;
    private final MemberRepository memberRepository;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "admin/dashboard";
    }

    @GetMapping("/admin/member/{memberId}")
    public String showMemberDetails(@PathVariable Long memberId, Model model) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        List<HabitTracker> trackers = habitTrackerService.getHabitTrackersByMemberId(member.getId());

        List<HabitTrackerProgressDto> dtoList = trackers.stream()
                .map(HabitTrackerProgressDto::from)
                .collect(Collectors.toList());

        model.addAttribute("member", member);
        model.addAttribute("dtoList", dtoList);
        return "admin/member_detail";
    }
}
