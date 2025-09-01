import Model
import ProfileCardEditFeature
import SearchFeature
import SwiftUI
import TimetableDetailFeature

// Navigation destinations for the entire app
public enum NavigationDestination: Hashable {
    case timetableDetail(TimetableItemWithFavorite)
    case search
    case profileCardEdit
}

// Navigation handler that can be passed down
public struct NavigationHandler {
    let handleSearchNavigation: (SearchNavigationDestination) -> Void

    public init(handleSearchNavigation: @escaping (SearchNavigationDestination) -> Void) {
        self.handleSearchNavigation = handleSearchNavigation
    }
}

// Extension to create views from navigation destinations
extension NavigationDestination {
    @ViewBuilder
    @MainActor
    func view(with navigationHandler: NavigationHandler) -> some View {
        switch self {
        case .timetableDetail(let item):
            TimetableDetailScreen(timetableItem: item)
        case .search:
            SearchScreen(onNavigate: navigationHandler.handleSearchNavigation)
        case .profileCardEdit:
            ProfileCardEditScreen()
        }
    }
}
